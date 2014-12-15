(ns clj-fn.time
  (require [clj-time.format :as f]
           [clj-time.core :as t]
           [clj-time.coerce :as tc]))

;; (f/unparse (fmt-shanghai "yyyy-MM-dd" ) (t/now))
(defn fmt-shanghai [& [str-fmt]]
  (let [fmt (or str-fmt "yyyy-MM-dd")]
    (f/formatter fmt (t/time-zone-for-id "Asia/Shanghai"))))

(defn today []
  (f/unparse (fmt-shanghai "yyyy-MM-dd" ) (t/now)))

;; (time-str)
(defn time-str [& [str-fmt]]
  (let [fmt (or str-fmt "yyyy-MM-dd H:m:s")]
    (f/unparse (fmt-shanghai fmt) (t/now))))

(defn d-of [secs dstr1 dstr2 & [fmt]]
  (let [fmt (or fmt (fmt-shanghai "yyyyMMdd"))
        dd1 (f/parse fmt dstr1)
        dd2 (f/parse fmt dstr2)
        dl1 (tc/to-long dd1)
        dl2 (tc/to-long dd2)]
    (/ (- dl2 dl1) (* secs 1000))))

(defn d-of-day [dstr1 dstr2 & [fmt]]
  (d-of (* 3600 24) dstr1 dstr2 fmt))

(defn d-of-hour [dstr1 dstr2 & [fmt]]
    (d-of 3600 dstr1 dstr2 fmt))


;;
;; (today)
;;
;; (d-of-day "20140101" "20140108")
;;
;; (def f "yyyy-MM-dd"
;;
;; (d-of-day "2014-01-01" "2014-01-13" f)
;;
;; (d-of-hour "2014-01-01" "2014-01-13" f)
