(ns ferry-front.views.booking
  (:require
    [ferry-front.components.booking-form :as booking-form]
    [ferry-front.components.timetables :as timetables]))


(defn booking-main []
  [:div
    [booking-form/booking-form]
    [timetables/booking-timetable]])

(defn booking-details [])
