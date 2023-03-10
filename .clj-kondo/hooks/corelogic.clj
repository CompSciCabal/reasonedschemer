(ns hooks.corelogic
  (:require
    [clj-kondo.hooks-api :as api]))

(defn run*
  [{:keys [node]}]
  (let [[_ args & body] (:children node)
        syms (:children args)
        let-bindings (mapcat (fn [sym] [sym (api/token-node 'nil)]) syms)]
    {:node (api/list-node
             (into [(api/token-node 'let)
                    (api/vector-node let-bindings)]
                   body))}))

(defn run
  [{:keys [node]}]
  (let [[_ _ args & body] (:children node)
        syms (:children args)
        let-bindings (mapcat (fn [sym] [sym (api/token-node 'nil)]) syms)]
    {:node (api/list-node
             (into [(api/token-node 'let)
                    (api/vector-node let-bindings)]
                   body))}))
