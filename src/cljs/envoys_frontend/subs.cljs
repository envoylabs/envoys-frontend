(ns envoys-frontend.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:title db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 ::hero-text
 (fn [db _]
   (:hero-text db)))

(re-frame/reg-sub
 ::loading?
 (fn [db _]
   (:loading? db)))
