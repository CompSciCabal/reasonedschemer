; vim: lispwords=fresh,run,run*:sw=2:
(ns reasonedschemer.ch1
  (:require
    [clojure.core.logic :refer [conde fresh fail run* run s# u# == firsto resto lcons conso]]
    [reasonedschemer.util :refer [defrel]]))


(run* [q] u#) ; ()

(run* [q] fail) ; ()

(run* [q]
  (== 'pea 'pod)) ; ()

(run* [q]
  (== q 'pea)) ; (pea)

(run* [q]
  (== 'pea q)) ; (pea)

(run* [q] s#) ; (_0)

(run* [q r]
  (== q (* 2 r))
  (== r 5))

(run* [q]
  (== 'pea 'pea)) ; (_0)

(run* [q]
  (== q q)) ; (_0)

(run* [q]
  (fresh [x]
    (== 'pea q))) ; (pea)

(run* [q]
  (fresh [x]
    (== 'pea x))) ; (_0)

(cons 1 '()) ; (1)

(cons 2 (cons 1 '())) ; (2 1)

(run* [q]
  (fresh [x]
    (== (cons x '()) q))) ; ((_0))

(run* [q]
  (fresh [x]
    (== (list x) q))) ; ((_0))

(run* [q]
  (fresh [x]
    (== q (* 2 x))))

(run* [q]
  (fresh [x]
    (== x q))) ; (_0)

(run* [q]
  (fresh [x]
    (fresh [y]
      (== (list y x y) q)))) ; ((_0 _1 _0))

;; First week ended with "The Second Law of ==" on page 24

(run* [q]
  s# s#) ; (_0)

(run* [q]
  s# (== 'corn q)) ; (corn)

(run* [q]
  u# (== 'corn q)) ; ()

(run* [q]
  (== 'corn q)
  (== 'meal q)) ; ()

(run* [q]
  (== 'corn q)
  (== 'corn q)) ; (corn)

(run* [q]
  (conde [u#] [u#])) ; ()

(run* [q]
  (conde [(== 'olive q)] [u#])) ; (olive)

(run* [q]
  (conde
    [u#]
    [(== 'oil q)])) ; (oil)

(run* [q]
  (conde
    [(== 'olive q)]
    [(== 'oil q)])) ; (olive oil)

(run* [q]
  (fresh [x]
    (fresh [y]
      (conde
        [(== (list x y) q)]
        [(== (list y x) q)])))) ; ((_0 _1) (_0 _1))

(run* [q]
  (fresh [x]
    (fresh [y]
      (== (list x y) q)
      (== (list y x) q)))) ; ((_0 _0))

(run* [x]
  (conde
    [(== 'olive x) u#]
    [(== 'oil x)])) ; (oil)

(run* [x]
  (conde
    [(== 'olive x) s#]
    [(== 'oil x)])) ; (olive oil)

(run* [x]
  (conde
    [(== 'oil x)]
    [(== 'olive x) s#])) ; (oil olive)

(run* [x]
  (conde
    [(== 'virgin x) u#]
    [(conde
       [(== 'olive x)]
       [(conde
          [s#]
          [(== 'oil x)])])])) ; (olive _0 oil)

(run* [r]
  (fresh [x]
    (fresh [y]
      (== 'split x)
      (== 'pea y)
      (== (list x y) r)))) ; ((split pea))

(run* [r]
  (fresh [x y]
    (== 'split x)
    (== 'pea y)
    (== (list x y) r))) ; ((split pea))

(run* [r x y]
  (== 'split x)
  (== 'pea y)
  (== (list x y) r)) ; ([(split pea) split pea])

(run* [x y]
  (fresh [r]
    (== 'split x)
    (== 'pea y)
    (== (list x y) r))) ; ([split pea])

(run* [x y]
  (conde
    [(== 'split x) (== 'pea y)]
    [(== 'red x) (== 'bean y)])) ; ([split pea] [red bean])

(run* [r]
  (fresh [x y]
    (conde
      [(== 'split x) (== 'pea y)]
      [(== 'red x) (== 'bean y)])
    (== (list x y 'soup) r))) ; ((split pea soup) (red bean soup))

(defrel teacupo [t]
  (conde [(== 'tea t)] [(== 'cup t)]))

(run* [x]
  (teacupo x)) ; (tea cup)

(run* [x y]
  (conde
    [(teacupo x) (== true y)]
    [(== false x) (== true y)])) ; ([false true] [tea true] [cup true])

(run* [x y]
  (teacupo x)
  (teacupo y)) ; ([tea tea] [tea cup] [cup tea] [cup cup])

(run* [x y]
  (teacupo x)
  (teacupo x)) ; ([tea _0] [cup _0])

(run* [x y]
  (conde
    [(teacupo x) (teacupo x)]
    [(== false x) (teacupo y)])) ; ([false tea] [false cup] [tea _0] [cup _0])


;; Start on 2023-03-09 on frame 88

(run* [x]
  (conde
    [(== 'olive x) u#]
    [(== 'oil x)])) ; (oil)

(run* [x y]
  (conde
    [(fresh [z]
       (== 'lentil z))]
    [(== x y)])) ; ([_0 _0] [_0 _1])

(run* [x y]
  (conde
    [(== 'split x) (== 'pea y)]
    [(== 'red x) (== 'bean y)]
    [(== 'green x) (== 'lentil y)])) ; ([split pea] [red bean] [green lentil])

;; Chapter 2

(first '(grape raisin pear)) ; grape

(first '(a c o r n)) ; a

(run* [q]
  (firsto '(a c o r n) q)) ; (a)

(run* [q]
  (firsto '(a c o r n) 'a)) ; (_0)

(run* [r]
  (fresh [x y]
    (firsto (list r y) x)
    (== 'pear x))) ; (pear)

(cons
  (first '(grape raisin pear))
  (first '((a) (b) (c)))) ; (grape a)

(run* [r]
  (fresh [x y]
    (firsto '(grape raisin pear) x)
    (firsto '((a) (b) (c)) y)
    (== (cons x y) r))) ; ((grape (a)))

(rest '(grape raisin pear)) ; (raisin pear)

(first (rest (rest '(a c o r n)))) ; o

(run* [r]
  (fresh [v]
    (resto '(a c o r n) v)
    (fresh [w]
      (resto v w)
      (firsto w r)))) ; (o)

(defrel cdro
  [l v]
  (fresh [a]
    (== (lcons a v) l)))

(run* [v]
  (cdro '(a b c) v)) ; ((b c))

(cons
  (rest '(grape raisin pear))
  (first '((a) (b) (c)))) ; ((raisin pear) a)

(run* [r]
  (fresh [x y]
    (resto '(grape raisin pear) x)
    (firsto '((a) (b) (c)) y)
    (== (lcons x y) r))) ; (((raisin pear) a))

(run* [q]
  (resto '(a c o r n) '(c o r n))) ; (_0)

(run* [x]
  (resto '(c o r n) (list x 'r 'n))) ; (o)

(run* [l]
  (fresh [x]
    (resto l '(c o r n))
    (firsto l x)
    (== 'a x))) ; ((a c o r n))

(run* [l]
  (conso '(a b c) '(d e) l)) ; (((a b c) d e))

(cons '(a b c) '(d e)) ; ((a b c) d e)

(run* [x]
  (conso x '(a b c) '(d a b c))) ; (d)

(run* [r]
  (fresh [x y z]
    (== (list 'e 'a 'd x) r)
    (conso y (list 'a z 'c) r))) ; ((e a d c))

(run* [x]
  (conso x (list 'a x 'c) (list 'd 'a x 'c))) ; (d)

(run* [l]
  (fresh [x]
    (== (list 'd 'a x 'c) l)
    (conso x (list 'a x 'c) l))) ; ((d a d c))

(run* [l]
  (fresh [x]
    (conso x (list 'a x 'c) l)
    (== (list 'd 'a x 'c) l))) ; ((d a d c))

(defrel conso2
  [v l x]
  (conde
    [(firsto x v)
     (resto x l)])) ; #'reasonedschemer.ch1/conso2

(run* [l v]
  (conso2 v (list 'b 'c v) l)) ; ((a b c))
; ([(_0 b c _0) _0])

(defrel conso3
  [v l x]
  (== (lcons v l) x))

(run* [l]
  (conso3 'a (list 'b 'c) l)) ; ((a b c))

;; Resume on 2023-03-16 on panel 28

