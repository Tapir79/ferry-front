(ns ferry-front.views.booking
  (:require
    [ferry-front.views.booking-form :as booking-form]
    [ferry-front.views.timetables :as timetables]))


(defn booking []
  [booking-form/booking-form]
  [timetables/booking-timetable])
