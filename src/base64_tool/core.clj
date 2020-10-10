(ns base64-tool.core
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:import
   (java.util Base64)
   (java.io OutputStream)))

(def default-file-src "base64.txt")
(def default-output-file "temp.png")

(defn read-file-string
  [file-path]
  (let [fp (if (nil? file-path) default-file-src file-path)]
    (slurp fp)))

(defn byte-output-stream
  [file-path]
  (let [fp (if (nil? file-path) default-output-file file-path)]
    (io/output-stream fp)))

(defn decode
  [str]
  (byte-array (.decode (Base64/getDecoder) str)))

(defn write-byte-array-file
  [input ^OutputStream out]
  (with-open [os out]
    (.write os input)))

(defn decode-png
  "write base64 to file"
  [& args]
  (let [in       (first args)
        out       (second args)]
    (write-byte-array-file (decode (read-file-string in)) (byte-output-stream out))))

(defn -main
  [& args]
  (decode-png args))
