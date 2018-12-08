(ns ferry-front.components.timetables
  (:require [re-frame.core :as rf]
            [ferry-front.subs.booking :as s-booking]
            [stylefy.core :as stylefy]
            [cljs-time.format :as tf]
            [reitit.frontend.easy :as rfe]
            [ferry-front.styles.global :refer [mobile-width]]
            [ferry-front.events.booking :as e-booking]))


(def date-style {:flex "0 0 12.5%" :text-align "left"})
(def time-style {:flex "0 0 10%" :text-align "center"})
(def vessel-style {:flex "0 0 20%" :text-align "left"})
(def status-style {:flex "0 0 15%" :text-align "center"})

(def row-style-str
  "flex justify-between items-center rounded-t bg-white border-b px-3 py-1 mb-px ")

(def item-row-style-str "cursor-pointer rounded p-3 text-gray hover:bg-grey-lighter")

(def time-formatter (tf/formatter "HH.mm"))

(def date-formatter (tf/formatter "dd.MM.yyyy"))

(defn timestamp-to-time [timestamp]
  (tf/unparse time-formatter (tf/parse timestamp)))

(defn timestamp-to-date [timestamp]
  (tf/unparse date-formatter (tf/parse-local timestamp)))

(defn get-status-color [unit]
  "rounded-full border-solid border-green text-grey-darker border-2 mx-1 h-8 w-8 flex justify-center items-center")

(defn handle-route-click [route]
  (rf/dispatch[::e-booking/select-route route])
  (rfe/push-state :confirm-booking))

(defn reservation-status [route]
  [:div {:class "flex justify-center"}
   [:div {:class (get-status-color (:cars route))}
    [:i {:class "fas fa-car-side"}]]
   [:div {:class (get-status-color (:passengers route))}
    [:i {:class "fas fa-male mr-px"}]
    [:i {:class "fas fa-female"}]]])

(defn timetable [stop-routes]
  [:div {:class "bg-blue-lighter p-1 sm:p-2 rounded"}
   [:ul {:class "list-reset"}
    [:li {:class (str row-style-str " font-semibold bg-blue text-white" )}
     [:div (stylefy/use-style date-style) "Date"]
     [:div (stylefy/use-style time-style) "Departs"]
     [:div (stylefy/use-style time-style) "Arrives"]
     [:div (stylefy/use-style vessel-style) "Vessel"]
     [:div (stylefy/use-style status-style) "Booking status"]]
    (for [route @stop-routes]
      [:li {:key (:segment_id route)
            :class (str row-style-str item-row-style-str)
            :on-click #(handle-route-click route) }
       [:div (stylefy/use-style date-style) (timestamp-to-date (:start_time route))]
       [:div (stylefy/use-style time-style) (timestamp-to-time (:start_time route))
        [:i {:class "ml-1 fas fa-arrow-right text-sm text-grey-darker"}]]
       [:div (stylefy/use-style time-style)
        [:i {:class "mr-1 fas fa-anchor text-sm text-grey-darker"}]
       (timestamp-to-time (:end_time route))]
       [:div (stylefy/use-style vessel-style) (:ship_name route)]
       [:div (stylefy/use-style status-style) (reservation-status route)]])]])

(defn booking-timetable []
  (let [stop-routes (rf/subscribe [::s-booking/stop-routes])]
    (when-not (empty? @stop-routes))
    (timetable stop-routes)))

