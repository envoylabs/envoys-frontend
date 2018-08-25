(ns envoys-frontend.events
  (:require
   [re-frame.core :as re-frame]
   [envoys-frontend.db :as db]
   [envoys-frontend.routes :as routes]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
            (assoc db :loading? true)
            (re-frame.core/dispatch [::set-page-contents active-panel])
            (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 ::init-backend
 (fn-traced [_ _]))

(re-frame/reg-event-fx
 ::set-page-contents
 (fn
  [{db :db} [_ active-panel]]

  (let [endpoint (active-panel routes/backend-mapping)]
    {:http-xhrio {:method :get
                  :uri endpoint
                  :format (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true}) 
                  :on-success [::handle-response]
                  :on-failure [::handle-bad-response]}})))

(re-frame/reg-event-db                   
 ::handle-response             
 (fn-traced
  [db [_ response]]
  (-> db
      (assoc :loading? false)
      (assoc :hero-text (:hero-text response)))))

(re-frame/reg-event-db
 ::handle-bad-response
 (fn-traced
  [db [_ response]]
  (-> db
      (assoc :loading? false)
      (assoc :hero-text "Envoys: at the bleeding edge of technology."))))

