(ns clj-fn.core
  (require [clj-yaml.core :as yaml]))

;; (def m (atom {:k1 0}))
;; (incm m :k1)
(defn incm [val key]
  (swap! val update-in [key] #(inc %)))


;;;;;;;;;;;;;;;; File System

;; (file-exists? "/tmp/")
(defn file-exists? [file]
  (.exists (clojure.java.io/as-file file)))

;; (safe-mkdir "/tmp/t1")
(defn safe-mkdir
  [path]
  (when-not (file-exists? path)
    (.mkdir (java.io.File. path))))

;;;;;;;;;;;;;;;; Configure
(def config (atom {}))
(defn load-conf [fname]
  (let [_fname (str fname ".mine")
        fname (if (file-exists? _fname) _fname fname)
        conf-str (slurp fname)
        conf-yaml (yaml/parse-string conf-str)]
    (reset! config conf-yaml)
    ))
