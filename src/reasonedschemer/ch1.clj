; vim: lispwords=fresh,run,run*:sw=2:
(ns reasonedschemer.ch1
  (:require
    [clojure.core.logic :refer [conda conde fresh fail run* run s# u# == firsto resto lcons lcons? conso nilo emptyo onceo]]
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
  [a d l]
  (firsto l a)
  (resto l d)) ; #'reasonedschemer.ch1/conso2

(run* [l v]
  (conso2 v (list 'b 'c v) l)) ; ((a b c))
; ([(_0 b c _0) _0])

(defrel conso3
  [a d l]
  (== (lcons a d) l))

(run* [l]
  (conso3 'a (list 'b 'c) l)) ; ((a b c))

;; Resume on 2023-03-16 on panel 28

(nil? '(grape raisin pair)) ; false

(nil? '()) ; false
(nil? nil) ; true

(run* [q] (nilo '(grape raisin pear))) ; ()

(run* [q] (nilo nil)) ; (_0)

(run* [x] (nilo x)) ; (nil)

(defrel nullo [x] (== x '( )))

(lcons 'split 'pea) ; (split . pea)
(lcons '. '.)  ; (. . .)
(run* [r]
  (fresh (x y)
    (== (lcons x (lcons y 'salad))
        r))) ; ((_0 _1 . salad))

(defrel pairo [p] (fresh (a d) (conso a d p)))

(run* [q] (pairo (lcons q q))) ; (_0)

(run* [q] (pairo '())) ; ()

(run* [q] (pairo 'pair))  ; ()

(run* [x] (pairo x)) ; ((_0 . _1))

(run* [r] (pairo (cons r '()))) ; (_0)

; (defn singleton? [l]
;   (cond
;          (lcons? l) (nil? (rest l))
;          :else false))

(defrel singletono [l]
  (fresh [d]
    (cdro l d)
    (nullo d)))

(defrel caro [l a]
  (fresh [d]
    (conso a d l)))

(defrel cdro [l d]
  (fresh [a]
    (conso a d l)))

(run* [l]
  (fresh [x]
    (cdro (list l x x) (list 'asd l))))

(defrel listo [l]
  (conde
    [(emptyo l)]
    [(fresh [d]
      (cdro l d)
      (listo d))]))

; Got to chapter 3, frame 9 right after the law of #s

(run 1 [x]
  (listo (lcons 'a (lcons 'b (lcons 'c x)))))

(run 5 [x]
  (listo (lcons 'a (lcons 'b (lcons 'c x)))))

(defrel lolo
  [l]
  (conde
    [(nullo l)]
    [(fresh [a d]
       (caro l a)
       (listo a)
       (cdro l d)
       (lolo d))]))

(run* [q]
  (fresh [x y]
    (lolo (list (list 'a 'b)
                (list x 'c)
                (list 'd y)))))

(run 6 [l]
  (lolo l))

(run 3 [q]
  (fresh [x]
    (lolo (lcons (list 'a 'b) x))))

(run 1 [x]
  (lolo (list (list 'a 'b)
              (lcons (list 'c 'd) x))))

(run 5 [x]
  (lolo (list (list 'a 'b)
              (lcons (list 'c 'd) x))))

(run 5 [x]
  (lolo (lcons (list 'a 'b)
               (lcons (list 'c 'd) x))))

(run 5 [x]
  (lolo x))

(defrel singletono [l]
  (fresh [a]
    (== l (list a))))

(defrel loso [l]
  (conde
    [(emptyo l)]
    [(fresh [a d]
       (caro l a)
       (singletono a)
       (cdro l d)
       (loso d))]))

(run 5 [z]
  (loso (lcons (list 'g) z)))

; Resume at chapter 3, panel 38

(run 4 [r]
  (fresh [w x y z]
    (loso
      (lcons (list 'g)
             (lcons (lcons 'e w)
                    (lcons (lcons x y)
                           z))))
    (== (list w (lcons x y) z) r)))


(defrel membero [x l]
  (conde
    [(fresh [d]
      (cdro l d)
      (membero x d))]
    [(fresh [a]
       (caro l a)
       (== a x))]))

(defrel membero2 [x l]
  (fresh [a d]
    (== (lcons a d) l)
    (conde
      [(== a x)]
      [(membero2 x d)])))

(defrel membero3 [x l]
  (conde
    [(caro l x)]
    [(fresh [d]
      (cdro l d)
      (membero3 x d))]))

(run 1 [x]
  (membero2 'oliver (list 'virgin 'olive 'oil)))

; Resume at ch 3, panel 46

(run 1 [y]
  (membero3 y (list 'hummus 'with 'pita)))

(run* [y]
  (membero3 y '(hummus (with pita))))

(run* [y]
  (membero3 y (lcons 'pear
                     (lcons 'grape 'peaches))))

(run* [x]
  (membero3 'e (list 'pasta x 'e 'fagioli)))

(run* [x y]
  (membero 'e (list 'pasta x 'fagioli y)))

(run* [q]
  (fresh [x y]
    (== (list 'pasta x 'fagioli y) q)
    (membero 'e q)))

(run 1 [l]
  (membero 'tofu l))

;(_.0 . ('tofu . _.1)) ???
(run 5 [l]
  (membero 'tofu l))

(defrel proper-membero [x l]
  (conde
    [(caro l x)
     (fresh [d]
       (cdro l d)
       (listo d))]
    [(fresh [d]
      (cdro l d)
      (proper-membero x d))]))

(run 12 [l]
  (proper-membero 'tofu l))

(def car first)
(def cdr rest)

(defn proper-member?
  [x l]
  (cond
    (empty? l)    false
    (= x (car l)) (list? (cdr l))
    :else         (proper-member? x (cdr l))))

(proper-member? 'a '(a b c))

(defrel appendo
  [l t out]
  (conde
    [(emptyo l) (== t out)]
    [(fresh [a d res]
       (conso a d l)
       (conso a res out)
       (appendo d t res))]))

(run 1 [x]
  (appendo '(a b c) x '(a b c d e)))

(run 6 [x]
  (fresh [y z]
    (appendo x y z)))

(run 6 [y]
  (fresh [x z]
    (appendo x y z)))

(run 6 [z]
  (fresh [x y]
    (appendo x y z)))

(run 6 [x y z]
  (appendo x y z))

(run 6 [z]
  (appendo '() 4 z))

; Continue on 2023-04-13 on ch 4, panel 23

(run 6 [x y]
  (appendo x y '(cake & ice d t)))

(run 20 [x y]
  (appendo x y '(cake & ice d t)))

(defrel swappendo
  [l t out]
  (conde
    [(fresh [a d res]
       (conso a d l)
       (conso a res out)
       (swappendo d t res))]
    [(emptyo l) (== t out)]))

(run* [x y]
  (swappendo x y '(cake & ice d t)))

(defrel unwrapo
  [x out]
  (conde
    [(fresh [a d]
       (== (lcons a d) x)
       (unwrapo a out))]
    [(== x out)]))

(run 4 [x]
  (unwrapo '((pizza)) x))

; Continue on 2023-04-20 on ch 4, panel 48

(run* [x]
  (unwrapo '(((pizza))) x))

(defrel memo
  [x l out]
  (conde
    [(caro l x)
     (== l out)]
    [(fresh [d]
       (cdro l d)
       (memo x d out))]))

(run* [q]
  (memo 'fig '(pea) '(pea)))

(run* [q]
  (memo 'fig '(fig pea fig fig) q))

(defrel rembero
  [x l out]
  (conde
    [(nullo l) (== '() out)]
    [(== (lcons x out) l)]
    [(fresh [a d res]
       (== (lcons a d) l)
       ;(conso a res out)
       (== (lcons a res) out)
       (rembero x d res))]))

(run 5 [y z w out]
  (rembero y (lcons z w) out))

; 0: x=y l=(z . w) out=out  630->  631->y=z,w=out, (_0 _0 _1 _1)
;    632->a=z,d=w,out=(z . ?), (rembero y w ?)
;   1: x=y,l=w,out=?  630->w=(),y=?,z=?,out=?, (_0 _1 '() '())
(run* [y z]
  (rembero y (list y 'd z 'e) (list y 'd 'e)))

; zeroth recursion x=y l=(y 'd z 'e) out=(y 'd 'e) => ('d 'd)
; first recursion x=y l=('d z 'e) out=('d 'e)      => ('d 'd)
; second recursion x=y l=(z 'e) out=('e)           => (_0 _0)
; third recursion x=y l=('e) out=()                => ('e 'e)

(run* [out]
  (rembero 'pea '(a pea b pea c) out))

(run* [out]
  (rembero 'pea '(a pea b pea c pea d) out))

(run* [out]
  (fresh [y z]
    (rembero y (list 'a 'b y 'd z 'e) out)))

(run* [out y z]
  (rembero y (list 'a 'b y 'd z 'e) out))

; Continue on 2023-04-27 on ch 5, panel 48

(run* [y z]
  (rembero y (list y 'd z 'e) (list y 'd 'e)))

(run 5 [y z w out]
  (rembero y (lcons z w) out))

; Resume on Chapter 6

(defrel alwayso
  []
  (conde
    [s#]
    [(alwayso)]))

; this hangs!
#_(run 1 [q]
  (alwayso)
  u#)

(run 1 [q]
  u#
  (alwayso))

(defrel nevero
  []
  (nevero))

(defrel very-recursiveo
  []
  (conde
    ((nevero))
    ((very-recursiveo))
    ((alwayso))
    ((very-recursiveo))
    ((nevero))))

(run 1000000 [q] (very-recursiveo))

(defrel bit-xoro
  [x y r]
  (conde
    ((== 0 x) (== 0 y) (== 0 r))
    ((== 0 x) (== 1 y) (== 1 r))
    ((== 1 x) (== 0 y) (== 1 r))
    ((== 1 x) (== 1 y) (== 0 r))))

(run* [x y]
  (bit-xoro x y 0))

(run* [x y]
  (bit-xoro x y 1))

(run* [x y r]
  (bit-xoro x y r))

(defrel bit-ando
  [x y r]
  (conde
    ((== 0 x) (== 0 y) (== 0 r))
    ((== 0 x) (== 1 y) (== 0 r))
    ((== 1 x) (== 0 y) (== 0 r))
    ((== 1 x) (== 1 y) (== 1 r))))

(run* [x y]
  (bit-ando x y 1))

(defrel half-addero
  [x y r c]
  (bit-xoro x y r)
  (bit-ando x y c))

(run* [r]
  (half-addero 1 1 r 1))

(run* [x y r c] (half-addero x y r c))

(defrel full-addero
  [b x y r c]
  (fresh [w xy wz]
    (half-addero x y w xy)
    (half-addero w b r wz)
    (bit-xoro xy wz c)))

(run* [r c]
  (full-addero 0 1 1 r c))

(defn build-num
  [n]
  (cond

    (and (not (zero? n)) (even? n))
    (cons 0 (build-num (int (/ n 2))))

    (odd? n)
    (cons 1 (build-num (int (/ n 2))))

    (zero? n)
    '()))

#_(build-num 19)
#_(build-num 36)

; Resume at 7.57

(defrel poso [n]
  (fresh [a d]
    (== (lcons a d) n)))

(run* [q]
  (poso '(0 1 1)))

(run* [q]
  (poso '()))

(run 5 [r]
  (poso r))

; The names a, ad, dd correspond...
(defrel >1o [n]
  (fresh (a ad dd)
    (== (lcons a (lcons ad dd)) n)))

(run* [q]
  (>1o '(0 1 1)))

(run* [q]
  (>1o '(1)))

(run* [r]
  (>1o r))

(run* [r]
  (== r '(1))
  (>1o r))


;; Resume at ch 7.104

(declare gen-addero)

(defrel addero [b n m r]
  (conde
    [(== 0 b) (== '() m) (== n r)]
    [(== 0 b) (== '() n) (== m r) (poso m)]
    [(== 1 b) (== '() m) (addero 0 n '(1) r)]
    [(== 1 b) (== '() n) (poso m) (addero 0 '(1) m r)]
    [(== '(1) n) (== '(1) m) (fresh [a c]
                               (== (list a c) r)
                               (full-addero b 1 1 a c))]
    [(== '(1) n) (gen-addero b n m r)]
    [(== '(1) m) (>1o n) (>1o r) (addero b '(1) n r)]
    [(>1o n) (gen-addero b n m r)]))

(defrel gen-addero [b n m r]
  (fresh [a c d e x y z]
    (== (lcons a x) n)
    (== (lcons d y) m) (poso y)
    (== (lcons c z) r) (poso z)
    (full-addero b a d c e)
    (addero e x y z)))

(run* [s]
  (gen-addero 1 '(0 1 1) '(1 1) s))

(defrel +o [n m k]
  (addero 0 n m k))

(run* [x y]
  (+o x y '(1 0 1)))

(defrel -o [n m k]
  (+o m k n))

(run* [q]
  (-o '(0 0 0 1) '(1 0 1) q))

(run* [q]
  (-o '(0 1 1) '(0 1 1) q))

(run* [q]
  (-o '(0 1 1) '(0 0 0 1) q))

(defrel lengtho [l r]
  (conde
    [(nullo l) (== r '())]
    [(fresh [a d r-1]
       (== (lcons a d) l)
       (+o r-1 '(1) r)
       (lengtho d r-1))]))

(run 1 [n]
  (lengtho '(jicama rhubarb guava) n))

(run* [ls]
  (lengtho ls '(1 0 1)))

(run* [q]
  (lengtho '(1 0 1) 3))

(run 3 [q]
  (lengtho q q))

(run 4 [q]
  (lengtho q q))

(defrel sumo [n m r]
  (conde
    [(nullo n) (== m r)]
    [(nullo m) (poso n) (== n r)]
    [(poso n) (poso m) (nullo r)
     (fresh [a x b y]
      (== (lcons a x) n)
      (== (lcons b y) m)
      (== x y)
      (bit-xoro a b 1))]
    [(poso n)
     (poso m)
     (>1o r)
     (fresh [a x b y c z]
       (== (lcons a x) n)
       (== (lcons b y) m)
       (== (lcons c z) r)
       (conde
         [(== a 0) (== b 0) (== c 0) (+o x y z)]
         [(== a 0) (== b 1) (== c 0) (-o x y z)]
         [(== a 1) (== b 0) (== c 0) (-o y x z)]
         [(== a 0) (== b 1) (== c 1) (-o y x z)]
         [(== a 1) (== b 0) (== c 1) (-o x y z)]
         [(== a 1) (== b 1) (== c 1) (+o x y z)]))]))

(run* [r]
  (sumo '(0 1) '(0 1) r))

(run* [r]
  (sumo '(0 1) '(1 1) r))

(run 10
  [n m]
  (sumo n '(0 1) m))

;; Resume at ch 7.126, which is just debugging the above

;; Resume at ch 8.31

(defrel <=lo
  [n m]
  (conde
    [(=lo n m)]
    [(<lo n m)]))

(defrel divo
  [n m q r]
  (conde
    [(<o n m) (== q '()) (== r n)]
    [(<=o m n)
     (<o r m)
     (fresh [mq]
       (*o m q mq)
       (+o mq r n))]))

;; Restart on ch8, frame 57

;; Restart on ch8, frame 78

(defrel expo
  [b q n]
  (logo n b q '()))

;; Resume on ch9

(defrel not-pastao
  [x]
  (conda
    [(== 'pasta x) u#]
    [s# s#]))

(run* [x]
  (conda
    [(not-pastao x) u#]
    [(== 'spaghetti x) s#]))

(run 5 [q]
  (conda
    [(alwayso) s#]
    [s# u#]))

;; Restart on ch9, frame 42

(defrel bumpo [n x]
  (conde
    [(== n x)]
    [(fresh [m]
       (-o n '(1) m)
       (bumpo m x))]))

#_(run* [x]
    (bumpo '(1 1 1) x))

(defrel enumerateo
  [op r n]
  (fresh [i j k]
    (bumpo n i)
    (bumpo n j)
    (op i j k)
    (onceo
      (fresh [x y z]
        (op x y z)
        (== i x)
        (== j y)
        (== k z)))
    (== (list i j k) r)))

#_(run* [s]
    (enumerateo +o s '(1 1)))

;; Chapter 10
;;
;; Notes on our Clojure implementation
;;
;; * Like Scheme, in Clojure we represent a single symbol in a vector to represent a var
;; ** We define var' instead of var to avoid the Clojure function of the same name
;; ** Perhaps we could have used just a symbol?
;; * We use a regular map to store a substitution list instead of a list of pairs
;; * The values of a substitution list are currently sets, but this may cause us problems in the future
;; ** Perhaps we should change this to a list


(defn var'
  [v]
  [v])

(defn var'?
  [v]
  (vector? v))

(def empty-s {})

(defn walk
  [v s]
  (if (contains? s v)
    (recur (get s v) s)
    v))

#_
(walk (var' 'z)
      {['x] ['y],
       ['y] 1})

;; Resume at ch 10, item 27

(defn occurs?
  [x v s]
  (let [v (walk v s)]
    (cond
      (var'? v) (= v x)
      (list? v) (some #{x} v))))

#_
(occurs? ['x] ['y] {['y] ['x]})

#_
(occurs? ['y] ['x] {['y] ['x]})

#_
(occurs? ['x] ['y] {['y] ['z],
                    ['z] ['x]})

#_
(occurs? ['x] ['y] {['y] ['z],
                    ['z] #{['x] 1}})

#_
(occurs? ['x] ['x] {})

#_
(occurs? ['x] #{['y]} {['y] ['x]})

(defn ext-s
  [x v s]
  (when-not (occurs? x v s)
    (assoc s x v)))

#_
(ext-s ['x] 1 {})

#_
(ext-s ['x] ['y] {['y] ['x]})

#_
(ext-s ['x] #{['x]} empty-s)

#_
(ext-s ['x] #{['y]} {['y] ['x]})

#_
(let [s {['z] ['x],
         ['y] ['z]}]
  (let [s (ext-s ['x] 'e s)]
    (and s (walk ['y] s))))

(defn unify
  [u v s]
  (let [u (walk u s)
        v (walk v s)]
    (cond

      (= u v) s

      (var'? u) (ext-s u v s)

      (var'? v) (ext-s v u s)

      (and (list? u)
           (list? v))
      (let [s (unify (first u) (first v) s)]
        (and s (unify (rest u) (rest v) s))))))

;; Resume on ch 10, frame 48

(def ss
  (fn [s]
    (list s)))

(def uu
  (fn [s]
    '()))

(defn ===
  [u v]
  (fn [s]
      (let [s (unify u v s)]
        (if s
            (list s)
            '()))))

#_((=== true false) empty-s)
#_((=== false false) empty-s)
#_((=== ['x] ['y]) empty-s)


(defn nil-or-empty?
  "An empty list in Clojure is _not_ the same as nil, so we have to check
  for either case where the book can just call (null? x)
  "
  [x]
  (or (nil? x)
      (and (sequential? x)
           (empty? x))))


(defn appendoo
  [soo too]
  (cond
    (nil-or-empty? soo) too
    (seq? soo)          (cons (first soo)
                              (appendoo (rest soo) too))
    :else               (fn []
                            (appendoo too (soo)))))


(defn disj2
  [g1 g2]
  (fn [s]
      (appendoo (g1 s) (g2 s))))

#_(takeoo 3 ((alwayso) empty-s))

#_(((nevero) empty-s))


(defn alwayso
  []
  (fn [s]
      (fn []
          ((disj2 ss (alwayso)) s))))


(defn takeoo
  [n soo]
  (cond
    (and n (zero? n)) '()
    (nil-or-empty? soo) '()
    (seq? soo)          (cons (first soo)
                              (takeoo (and n (dec n)) (rest soo)))
    :else               (takeoo n (soo))))


#_((=== 'olive ['x]) empty-s)
#_(takeoo 1 ((disj2 (=== 'olive ['x]) (=== 'oil ['x])) empty-s))


(defn append-mapoo
  [g soo]
  (cond
    (nil-or-empty? soo) '()
    (seq? soo)          (appendoo (g (first soo))
                                  (append-mapoo g (rest soo)))
    :else               (fn [] (append-mapoo g (soo)))))


(defn conj2
  [g1 g2]
  (fn [s]
      (append-mapoo g2 (g1 s))))


#_((conj2 (=== ['x] 1) (=== ['x] 2)) empty-s)
#_((conj2 (=== ['x] 1) (=== ['y] 2)) empty-s)


(defn call-fresh
  [name f]
  (f (var' name)))

#_(takeoo 1 ((call-fresh 'kiwi (fn [fruit] (=== 'plum fruit))) empty-s))


(defn reify-name
  [n]
  (symbol (str "_" n)))

#_(reify-name 27)


(defn walk*
  [v s]
  (let [v (walk v s)]
    (cond
      (var'? v) v
      (seq? v) (cons (walk* (first v) s)
                     (walk* (rest v) s))
      :else v)))


(defn reify-s
  [v r]
  (let [v (walk v r)]
    (cond
      (var'? v) (let [n (count r)
                      rn (reify-name n)]
                  (assoc r v rn))
      (seq? v) (let [r (reify-s (first v) r)]
                 (reify-s (cdr v) r))
      :else r)))


(defn reify' ; avoid clojure.core/reify
  [v]
  (fn [s]
      (let [v (walk* v s)
            r (reify-s v empty-s)]
        (walk* v r))))


#_(map (reify ['x]) (takeoo 5 ((disj2 (=== 'olive ['x]) (=== 'oil ['x])) empty-s)))


(defn run-goal
  [n g]
  (takeoo n (g empty-s)))


#_(map (reify ['x]) (run-goal 5 (disj2 (=== 'olive ['x]) (=== 'oil ['x]))))


