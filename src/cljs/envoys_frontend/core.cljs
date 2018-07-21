(ns envoys-frontend.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [envoys-frontend.events :as events]
   [envoys-frontend.routes :as routes]
   [envoys-frontend.views :as views]
   [envoys-frontend.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
