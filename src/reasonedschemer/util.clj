(ns reasonedschemer.util
  (:require
    [clojure.core.logic :refer [conde]]))

; Thanks to Alexander Fertman

(defmacro defrel
  [rel arglist & goals]
  `(defn ~rel ~arglist
     (fn
       [s#]
       (fn
         []
         ((conde [~@goals]) s#)))))
