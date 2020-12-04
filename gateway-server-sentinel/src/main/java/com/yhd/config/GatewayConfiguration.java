package com.yhd.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 限流规则配置类
 *
 * @author 05887-zhangfei
 * @version Id: GatewayConfiguration.java, v 0.1 2020/11/13 11:40 zhangfei Exp $
 */
@Configuration
public class GatewayConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    /**
     * 构造器
     *
     * @param viewResolversProvider
     * @param serverCodecConfigurer
     */
    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 限流异常处理器
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 限流过滤器
     *
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
        //加载网关限流规则
        initGatewayRules();
        //自定义限流异常处理
        initBlockHandler();
    }

    /**
     * 网关限流规则
     */
    private void initGatewayRules() {
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        /**
//         * resource: 资源名称， 可以是网关中的route名称或用户自定义API分组名称
//         * count: 限流阀值
//         * intervalSec: 统计时间窗口，单位是秒，默认是1秒
//         */
//        rules.add(new GatewayFlowRule("order-service")
//                .setCount(3) // 限流阈值
//                .setIntervalSec(60) // 统计时间窗口，单位是秒，默认是 1 秒
//        );
//        //加载网关限流规则
//        GatewayRuleManager.loadRules(rules);

        //限流分组
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new
                GatewayFlowRule("product_api")
                .setCount(3)  //限流阀值
                .setIntervalSec(60)); //统计时间窗口
        rules.add(new
                GatewayFlowRule("order_api")
                .setCount(5)  //限流阀值
                .setIntervalSec(60)); //统计时间窗口
        //加载网关限流规则
        GatewayRuleManager.loadRules(rules);
        //加载限流分组
        initCustomizedApis();

    }

    /**
     * 自定义限流异常处理
     * ResultSupport 为自定义的消息封装类，代码略
     */
    private void initBlockHandler() {
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange,
                                                      Throwable throwable) {
                Map<String, String> result = new HashMap<String, String>();
                result.put("code", String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()));
                result.put("message", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
                result.put("route", "order-service");

                return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(result));
            }
        });
    }

    /**
     * 限流分组
     */
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        //product_api组
        ApiDefinition api1 = new ApiDefinition("product_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    // 以/product-service/product 开头的请求
                    add(new ApiPathPredicateItem().setPattern("/product-service/product/**").
                            setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});
        //order_api组
        ApiDefinition api2 = new ApiDefinition("order_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    // 以/product-service/order/index 完成的url路径匹配
                    add(new ApiPathPredicateItem().setPattern("/order-service/order/index"));
                }});
        definitions.add(api1);
        definitions.add(api2);
        //加载限流分组
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }
}
