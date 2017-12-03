package com.joybar.librouter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joybar on 2017/11/13.
 */

public class Router {

    public static Map<Rule, Rule> ruleMap = new HashMap<>();

    public static void registerRouter(String module, String path, Class clazz) {
        Rule rule = new Rule(module, path, clazz);
        ruleMap.put(rule, rule);
    }


    public static void registerRouters(RouterTable routeTable) {
        List<Rule> rules = routeTable.buildRuleList();
        if (null != rules && rules.size() != 0) {
            for (Rule rule : rules) {
                ruleMap.put(rule, rule);
            }
        }
    }


    public static IRouterManagerService create() {
        RouterService routerService = new RouterService();
        return routerService;
    }


    public interface RouterTable {
        List<Rule> buildRuleList();

    }

}
