(ns reasonedschemer.util)

; Thanks to Alexander Fertman

(defmacro defrel
  [rel arglist goal]
  `(defn ~rel ~arglist
     (fn
       [s#]
       (fn
         []
         (~goal s#)))))


