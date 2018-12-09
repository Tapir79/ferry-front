(ns ferry-front.db)

(def default-db
  {:name "Smooth sailing front app"
   :stop-routes nil
   :lines nil
   :stops nil
   :test nil
   :tests nil
   :linesegments nil
   ; :sodra :norra :tvar
   :chosen-line-geom nil
   :line 0
   :booking-line 0
   :booking-arrival-stop nil
   :booking-departure-stop nil
   :booking-selected-route nil
   :booking-status-count nil})

