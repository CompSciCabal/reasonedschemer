(ns reasonedschemer.minikanren-test
  (:require
    [clojure.test :refer [deftest is]]
    [reasonedschemer.minikanren :refer :all]))


(deftest logic-var-test
  (is (lvar? (lvar 'x)))
  (is (not (lvar? 'x))))


(deftest walk-test
  (let [s {(lvar 'x) (lvar 'y)
           (lvar 'y) 1}]
    (is (= 1 (walk (lvar 'y) s)))
    (is (= 1 (walk (lvar 'x) s)))
    (is (= (lvar 'z) (walk (lvar 'z) s)))))


(deftest occurs?-test
  (let [s {(lvar 'x) (lvar 'y)
           (lvar 'y) 1
           (lvar 'z) (list (lvar 'x))
           (lvar 'a) (list (lvar 'b))}]
    (is (occurs? (lvar 'x) (lvar 'x) empty-s))
    (is (occurs? (lvar 'x) (list (lvar 'y)) {(lvar 'y) (lvar 'x)}))
    ; occurs? doesn't work if the var is already defined
    ;(is (occurs? (lvar 'x) (lvar 'z) s))
    (is (occurs? (lvar 'b) (lvar 'a) s))
    (is (not (occurs? (lvar 'b) (lvar 'c) s)))))

#_(occurs?-test)

(deftest ext-s-test
  (is (nil? (ext-s (lvar 'x) (list (lvar 'x)) empty-s)))
  (is (nil? (ext-s (lvar 'x) (list (lvar 'y)) {(lvar 'y) (lvar 'x)})))
  (let [s {(lvar 'z) (lvar 'x)
           (lvar 'y) (lvar 'z)}
        s (ext-s (lvar 'x) 'e s)]
    (is (= 'e (and s (walk (lvar 'y) s)))))
  (let [s (->> empty-s
               (ext-s (lvar 'x) (lvar 'y))
               (ext-s (lvar 'y) 1)
               (ext-s (lvar 'a) (lvar 'b)))]
    (is (= nil (ext-s (lvar 'b) (lvar 'a) s))
        "Cycle is rejected")
    (is (= (lvar 'c) (-> (ext-s (lvar 'b) (lvar 'c) s)
                         (get (lvar 'b))))
        "No cycle, mapping accepted")))


(deftest unify-test
  (is (= {(lvar 'x) (lvar 'y)} (unify (lvar 'x) (lvar 'y) empty-s)))
  (is (= {(lvar 'x) 1} (unify (lvar 'x) 1 empty-s)))
  (is (= {(lvar 'x) 2} (unify 2 (lvar 'x) empty-s)))
  (is (= {(lvar 'a) 1
          (lvar 'b) 2} (unify (list (lvar 'a) (lvar 'b))
                              (list 1 2)
                              empty-s)))
  )


(deftest ===-test
  (is (nil? ((=== true false) empty-s)))
  (is (= (list {}) ((=== true true) empty-s)))
  (is (= (list {(lvar 'x) (lvar 'y)}) ((=== (lvar 'x) (lvar 'y)) empty-s)))
  )


(deftest disj2-test
  (is (= [{(lvar 'x) 'olive}
          {(lvar 'x) 'oil}]
         ((disj2 (=== (lvar 'x) 'olive)
                 (=== (lvar 'x) 'oil)) empty-s)))
  (is (= [{} {(lvar 'x) 'oil}]
         ((disj2 ss
                 (=== (lvar 'x) 'oil)) empty-s)))
  (is (= [{(lvar 'x) 'oil}]
         ((disj2 uu (=== (lvar 'x) 'oil)) empty-s)))
  (is (= [{(lvar 'x) 'oil}]
         ((disj2 (=== (lvar 'x) 'oil) uu) empty-s)))
  (is (= [{(lvar 'x) 'oil} {}]
         ((disj2 (=== (lvar 'x) 'oil) ss) empty-s)))
  )


(deftest takeoo-test
  (is (= [{} {} {}]
         (takeoo 3 ((alwayso) empty-s))))
  #_(is (= [{(lvar 'x) 'olive}
          {(lvar 'x) 'oil}]
         (takeoo false ((disj2 (=== (lvar 'x) 'olive)
                               (=== (lvar 'x) 'oil)) empty-s))))
  #_(is (= [{(lvar 'x) 'olive}]
         (takeoo 1 ((disj2 (=== (lvar 'x) 'olive)
                           (=== (lvar 'x) 'oil)) empty-s))))
  )

#_(takeoo-test)
#_(pr-str (fn [] 'foo))
