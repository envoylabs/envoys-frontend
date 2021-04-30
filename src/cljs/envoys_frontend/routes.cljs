(ns envoys-frontend.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
   [secretary.core :as secretary]
   [goog.events :as gevents]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [envoys-frontend.events :as events]
   ))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (defroute "/" []
    (re-frame/dispatch [::events/set-active-panel :home-panel]))

  (defroute "/about" []
    (re-frame/dispatch [::events/set-active-panel :about-panel]))

  (defroute "/blog" []
    (set! (.. js/window -location -replace) "https://envoylabs.github.io/blog/")
    ;;(re-frame/dispatch [::events/set-active-panel :blog-panel])
    )

  (defroute "/contact" []
    (re-frame/dispatch [::events/set-active-panel :contact-panel]))

  (defroute "/contact/create" {:keys [query-params]}
    (re-frame/dispatch [::events/send-contact-form query-params]))
  ;; --------------------
  (hook-browser-navigation!))

