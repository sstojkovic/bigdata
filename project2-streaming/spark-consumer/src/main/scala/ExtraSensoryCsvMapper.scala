import scala.collection.mutable

class ExtraSensoryCsvMapper extends Serializable {
  def Map(csv: String): mutable.Map[String, String] = {
    val headerSchema = "timestamp,raw_acc:magnitude_stats:mean,raw_acc:magnitude_stats:std,raw_acc:magnitude_stats:moment3,raw_acc:magnitude_stats:moment4,raw_acc:magnitude_stats:percentile25,raw_acc:magnitude_stats:percentile50,raw_acc:magnitude_stats:percentile75,raw_acc:magnitude_stats:value_entropy,raw_acc:magnitude_stats:time_entropy,raw_acc:magnitude_spectrum:log_energy_band0,raw_acc:magnitude_spectrum:log_energy_band1,raw_acc:magnitude_spectrum:log_energy_band2,raw_acc:magnitude_spectrum:log_energy_band3,raw_acc:magnitude_spectrum:log_energy_band4,raw_acc:magnitude_spectrum:spectral_entropy,raw_acc:magnitude_autocorrelation:period,raw_acc:magnitude_autocorrelation:normalized_ac,raw_acc:3d:mean_x,raw_acc:3d:mean_y,raw_acc:3d:mean_z,raw_acc:3d:std_x,raw_acc:3d:std_y,raw_acc:3d:std_z,raw_acc:3d:ro_xy,raw_acc:3d:ro_xz,raw_acc:3d:ro_yz,proc_gyro:magnitude_stats:mean,proc_gyro:magnitude_stats:std,proc_gyro:magnitude_stats:moment3,proc_gyro:magnitude_stats:moment4,proc_gyro:magnitude_stats:percentile25,proc_gyro:magnitude_stats:percentile50,proc_gyro:magnitude_stats:percentile75,proc_gyro:magnitude_stats:value_entropy,proc_gyro:magnitude_stats:time_entropy,proc_gyro:magnitude_spectrum:log_energy_band0,proc_gyro:magnitude_spectrum:log_energy_band1,proc_gyro:magnitude_spectrum:log_energy_band2,proc_gyro:magnitude_spectrum:log_energy_band3,proc_gyro:magnitude_spectrum:log_energy_band4,proc_gyro:magnitude_spectrum:spectral_entropy,proc_gyro:magnitude_autocorrelation:period,proc_gyro:magnitude_autocorrelation:normalized_ac,proc_gyro:3d:mean_x,proc_gyro:3d:mean_y,proc_gyro:3d:mean_z,proc_gyro:3d:std_x,proc_gyro:3d:std_y,proc_gyro:3d:std_z,proc_gyro:3d:ro_xy,proc_gyro:3d:ro_xz,proc_gyro:3d:ro_yz,raw_magnet:magnitude_stats:mean,raw_magnet:magnitude_stats:std,raw_magnet:magnitude_stats:moment3,raw_magnet:magnitude_stats:moment4,raw_magnet:magnitude_stats:percentile25,raw_magnet:magnitude_stats:percentile50,raw_magnet:magnitude_stats:percentile75,raw_magnet:magnitude_stats:value_entropy,raw_magnet:magnitude_stats:time_entropy,raw_magnet:magnitude_spectrum:log_energy_band0,raw_magnet:magnitude_spectrum:log_energy_band1,raw_magnet:magnitude_spectrum:log_energy_band2,raw_magnet:magnitude_spectrum:log_energy_band3,raw_magnet:magnitude_spectrum:log_energy_band4,raw_magnet:magnitude_spectrum:spectral_entropy,raw_magnet:magnitude_autocorrelation:period,raw_magnet:magnitude_autocorrelation:normalized_ac,raw_magnet:3d:mean_x,raw_magnet:3d:mean_y,raw_magnet:3d:mean_z,raw_magnet:3d:std_x,raw_magnet:3d:std_y,raw_magnet:3d:std_z,raw_magnet:3d:ro_xy,raw_magnet:3d:ro_xz,raw_magnet:3d:ro_yz,raw_magnet:avr_cosine_similarity_lag_range0,raw_magnet:avr_cosine_similarity_lag_range1,raw_magnet:avr_cosine_similarity_lag_range2,raw_magnet:avr_cosine_similarity_lag_range3,raw_magnet:avr_cosine_similarity_lag_range4,watch_acceleration:magnitude_stats:mean,watch_acceleration:magnitude_stats:std,watch_acceleration:magnitude_stats:moment3,watch_acceleration:magnitude_stats:moment4,watch_acceleration:magnitude_stats:percentile25,watch_acceleration:magnitude_stats:percentile50,watch_acceleration:magnitude_stats:percentile75,watch_acceleration:magnitude_stats:value_entropy,watch_acceleration:magnitude_stats:time_entropy,watch_acceleration:magnitude_spectrum:log_energy_band0,watch_acceleration:magnitude_spectrum:log_energy_band1,watch_acceleration:magnitude_spectrum:log_energy_band2,watch_acceleration:magnitude_spectrum:log_energy_band3,watch_acceleration:magnitude_spectrum:log_energy_band4,watch_acceleration:magnitude_spectrum:spectral_entropy,watch_acceleration:magnitude_autocorrelation:period,watch_acceleration:magnitude_autocorrelation:normalized_ac,watch_acceleration:3d:mean_x,watch_acceleration:3d:mean_y,watch_acceleration:3d:mean_z,watch_acceleration:3d:std_x,watch_acceleration:3d:std_y,watch_acceleration:3d:std_z,watch_acceleration:3d:ro_xy,watch_acceleration:3d:ro_xz,watch_acceleration:3d:ro_yz,watch_acceleration:spectrum:x_log_energy_band0,watch_acceleration:spectrum:x_log_energy_band1,watch_acceleration:spectrum:x_log_energy_band2,watch_acceleration:spectrum:x_log_energy_band3,watch_acceleration:spectrum:x_log_energy_band4,watch_acceleration:spectrum:y_log_energy_band0,watch_acceleration:spectrum:y_log_energy_band1,watch_acceleration:spectrum:y_log_energy_band2,watch_acceleration:spectrum:y_log_energy_band3,watch_acceleration:spectrum:y_log_energy_band4,watch_acceleration:spectrum:z_log_energy_band0,watch_acceleration:spectrum:z_log_energy_band1,watch_acceleration:spectrum:z_log_energy_band2,watch_acceleration:spectrum:z_log_energy_band3,watch_acceleration:spectrum:z_log_energy_band4,watch_acceleration:relative_directions:avr_cosine_similarity_lag_range0,watch_acceleration:relative_directions:avr_cosine_similarity_lag_range1,watch_acceleration:relative_directions:avr_cosine_similarity_lag_range2,watch_acceleration:relative_directions:avr_cosine_similarity_lag_range3,watch_acceleration:relative_directions:avr_cosine_similarity_lag_range4,watch_heading:mean_cos,watch_heading:std_cos,watch_heading:mom3_cos,watch_heading:mom4_cos,watch_heading:mean_sin,watch_heading:std_sin,watch_heading:mom3_sin,watch_heading:mom4_sin,watch_heading:entropy_8bins,location:num_valid_updates,location:log_latitude_range,location:log_longitude_range,location:min_altitude,location:max_altitude,location:min_speed,location:max_speed,location:best_horizontal_accuracy,location:best_vertical_accuracy,location:diameter,location:log_diameter,location_quick_features:std_lat,location_quick_features:std_long,location_quick_features:lat_change,location_quick_features:long_change,location_quick_features:mean_abs_lat_deriv,location_quick_features:mean_abs_long_deriv,audio_naive:mfcc0:mean,audio_naive:mfcc1:mean,audio_naive:mfcc2:mean,audio_naive:mfcc3:mean,audio_naive:mfcc4:mean,audio_naive:mfcc5:mean,audio_naive:mfcc6:mean,audio_naive:mfcc7:mean,audio_naive:mfcc8:mean,audio_naive:mfcc9:mean,audio_naive:mfcc10:mean,audio_naive:mfcc11:mean,audio_naive:mfcc12:mean,audio_naive:mfcc0:std,audio_naive:mfcc1:std,audio_naive:mfcc2:std,audio_naive:mfcc3:std,audio_naive:mfcc4:std,audio_naive:mfcc5:std,audio_naive:mfcc6:std,audio_naive:mfcc7:std,audio_naive:mfcc8:std,audio_naive:mfcc9:std,audio_naive:mfcc10:std,audio_naive:mfcc11:std,audio_naive:mfcc12:std,audio_properties:max_abs_value,audio_properties:normalization_multiplier,discrete:app_state:is_active,discrete:app_state:is_inactive,discrete:app_state:is_background,discrete:app_state:missing,discrete:battery_plugged:is_ac,discrete:battery_plugged:is_usb,discrete:battery_plugged:is_wireless,discrete:battery_plugged:missing,discrete:battery_state:is_unknown,discrete:battery_state:is_unplugged,discrete:battery_state:is_not_charging,discrete:battery_state:is_discharging,discrete:battery_state:is_charging,discrete:battery_state:is_full,discrete:battery_state:missing,discrete:on_the_phone:is_False,discrete:on_the_phone:is_True,discrete:on_the_phone:missing,discrete:ringer_mode:is_normal,discrete:ringer_mode:is_silent_no_vibrate,discrete:ringer_mode:is_silent_with_vibrate,discrete:ringer_mode:missing,discrete:wifi_status:is_not_reachable,discrete:wifi_status:is_reachable_via_wifi,discrete:wifi_status:is_reachable_via_wwan,discrete:wifi_status:missing,lf_measurements:light,lf_measurements:pressure,lf_measurements:proximity_cm,lf_measurements:proximity,lf_measurements:relative_humidity,lf_measurements:battery_level,lf_measurements:screen_brightness,lf_measurements:temperature_ambient,discrete:time_of_day:between0and6,discrete:time_of_day:between3and9,discrete:time_of_day:between6and12,discrete:time_of_day:between9and15,discrete:time_of_day:between12and18,discrete:time_of_day:between15and21,discrete:time_of_day:between18and24,discrete:time_of_day:between21and3,label:LYING_DOWN,label:SITTING,label:FIX_walking,label:FIX_running,label:BICYCLING,label:SLEEPING,label:LAB_WORK,label:IN_CLASS,label:IN_A_MEETING,label:LOC_main_workplace,label:OR_indoors,label:OR_outside,label:IN_A_CAR,label:ON_A_BUS,label:DRIVE_-_I_M_THE_DRIVER,label:DRIVE_-_I_M_A_PASSENGER,label:LOC_home,label:FIX_restaurant,label:PHONE_IN_POCKET,label:OR_exercise,label:COOKING,label:SHOPPING,label:STROLLING,label:DRINKING__ALCOHOL_,label:BATHING_-_SHOWER,label:CLEANING,label:DOING_LAUNDRY,label:WASHING_DISHES,label:WATCHING_TV,label:SURFING_THE_INTERNET,label:AT_A_PARTY,label:AT_A_BAR,label:LOC_beach,label:SINGING,label:TALKING,label:COMPUTER_WORK,label:EATING,label:TOILET,label:GROOMING,label:DRESSING,label:AT_THE_GYM,label:STAIRS_-_GOING_UP,label:STAIRS_-_GOING_DOWN,label:ELEVATOR,label:OR_standing,label:AT_SCHOOL,label:PHONE_IN_HAND,label:PHONE_IN_BAG,label:PHONE_ON_TABLE,label:WITH_CO-WORKERS,label:WITH_FRIENDS,label_source,latitude,longitude"
    val headerParts = headerSchema.split(",")
    val dataParts = csv.split(",")

    if (headerParts.length != dataParts.length) {
      return null
    }

    val map = mutable.Map[String, String]()

    for (i <- 0 until headerParts.length) {
      map.put(headerParts(i), dataParts(i))
    }

    val isValid = map("timestamp").forall(Character.isDigit)

    if (isValid)
      map
    else
      null
  }
}

