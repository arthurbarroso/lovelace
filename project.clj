(defproject lovelace "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
                      :url "https://www.eclipse.org/legal/epl-2.0/"}
            :dependencies [[org.clojure/clojure "1.10.1"]
                           [clj-http "3.10.0"]
                           [cheshire "5.10.0"]]
            :profiles {:socket {:jvm-opts ["-Dclojure.server.myrepl={:port,5555,:accept,clojure.core.server/repl}"]}}
            :repl-options {:init-ns lovelace.core})
