(ns clj-fn.core)

(defn zws [v]
  (let [len (count v)
        vs (vec (sort v))
        p (int (/ len 2))]
    (case len
      0 0
      1 (vs 0)
      (if (odd? len)
        (vs p)
        (float (/ (+ (vs p)
                     (vs (- p 1)))
                  2))))))

   ;; (zws [2 5 1 7 6 3 9 8])

(defn incm [val key]
  (swap! val update-in [key] #(inc %)))

;; (def m (atom {:k1 0}))
;; (incm m :k1)

(defn file-exists [file]
  (.exists (clojure.java.io/as-file file)))


(def config (atom {}))
(defn load-conf [fname]
  (let [_fname (str fname ".mine")
        fname (if (file-exists _fname) _fname fname)
        conf-str (slurp fname)
        conf-yaml (yaml/parse-string conf-str)]
    (reset! config conf-yaml)
    ))
