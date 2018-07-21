(ns envoys-frontend.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [envoys-frontend.core-test]))

(doo-tests 'envoys-frontend.core-test)
