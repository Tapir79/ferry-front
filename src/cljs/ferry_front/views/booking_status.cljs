(ns ferry-front.views.booking-status
  (:require [re-frame.core :as rf]
            [ferry-front.subs.booking-status :as s-booking-status]))

(defn status-view []
  (let [booking (rf/subscribe [::s-booking-status/new-booking-status])]
    [:div {:class "p-3 rounded bg-blue-lighter"} "Socket show off test"
      [:p (str "last posted booking: " @booking)]]))
