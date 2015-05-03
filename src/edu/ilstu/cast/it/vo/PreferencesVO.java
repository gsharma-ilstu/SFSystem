package edu.ilstu.cast.it.vo;

import java.util.ArrayList;

public class PreferencesVO {

	String professorName;
        private CoursePreferenceVO courses;
        private String week;
	String[] days;
	String evening;
	String IT_115;
	String IT_140;
	String IT_150;
	String IT_164;
	String IT_165;
	String IT_214;
	String IT_390;
	String IT_168;
	String IT_178;
	String IT_179;
	String IT_191;
	String IT_225;
	String IT_226;
	String IT_254;
	String IT_261;
	String IT_262;
	String IT_275;

	String IT_272;
	String IT_279;
	String IT_326;
	String IT_327;
	String IT_328;
	String IT_330;
	String IT_332;
	String IT_340;
	String IT_341;
	String IT_350;
	String IT_351;
	String IT_353;
	String IT_354;
	String IT_356;
	String IT_357;
	String IT_363;
	String IT_367;
	String IT_372;
	String IT_373;

	String IT_375;
	String IT_376;
	String IT_377;
	String IT_378;
	String IT_379;
	String IT_380;
	String IT_381;
	String IT_382;
	String IT_383;
	String IT_384;
	String IT_388;
	String IT_391;
	String IT_432;
	String IT_450;
	String IT_463;
	String IT_467;

	String IT_468;
	String IT_477;
	String IT_478;
	String IT_495;
	String IT_496;
	String IT_497;
        public PreferencesVO()
        {
        }
        
        public PreferencesVO(String professorName, CoursePreferenceVO courses)
        {
            professorName=professorName;
            this.courses= courses;
        }
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	public String[] getDays() {
		return days;
	}
        public void setWeek(String days)
        {
            week=days;
        }
        public String getWeek(){
            return week;
        }
	public void setDays(String[] days) {
		this.days = days;
	}
	public String getEvening() {
		return evening;
	}
	public void setEvening(String evening) {
		evening = evening;
	}
	public String getIT_115() {
		return IT_115;
	}
	public void setIT_115(String iT_115) {
		IT_115 = iT_115;
	}
	public String getIT_140() {
		return IT_140;
	}
	public void setIT_140(String iT_140) {
		IT_140 = iT_140;
	}
	public String getIT_150() {
		return IT_150;
	}
	public void setIT_150(String iT_150) {
		IT_150 = iT_150;
	}
	public String getIT_164() {
		return IT_164;
	}
	public void setIT_164(String iT_164) {
		IT_164 = iT_164;
	}
	public String getIT_165() {
		return IT_165;
	}
	public void setIT_165(String iT_165) {
		IT_165 = iT_165;
	}
	public String getIT_214() {
		return IT_214;
	}
	public void setIT_214(String iT_214) {
		IT_214 = iT_214;
	}
	public String getIT_390() {
		return IT_390;
	}
	public void setIT_390(String iT_390) {
		IT_390 = iT_390;
	}
	public String getIT_168() {
		return IT_168;
	}
	public void setIT_168(String iT_168) {
		IT_168 = iT_168;
	}
	public String getIT_178() {
		return IT_178;
	}
	public void setIT_178(String iT_178) {
		IT_178 = iT_178;
	}
	public String getIT_179() {
		return IT_179;
	}
	public void setIT_179(String iT_179) {
		IT_179 = iT_179;
	}
	public String getIT_191() {
		return IT_191;
	}
	public void setIT_191(String iT_191) {
		IT_191 = iT_191;
	}
	public String getIT_225() {
		return IT_225;
	}
	public void setIT_225(String iT_225) {
		IT_225 = iT_225;
	}
	public String getIT_226() {
		return IT_226;
	}
	public void setIT_226(String iT_226) {
		IT_226 = iT_226;
	}
	public String getIT_254() {
		return IT_254;
	}
	public void setIT_254(String iT_254) {
		IT_254 = iT_254;
	}
	public String getIT_261() {
		return IT_261;
	}
	public void setIT_261(String iT_261) {
		IT_261 = iT_261;
	}
	public String getIT_262() {
		return IT_262;
	}
	public void setIT_262(String iT_262) {
		IT_262 = iT_262;
	}
	public String getIT_275() {
		return IT_275;
	}
	public void setIT_275(String iT_275) {
		IT_275 = iT_275;
	}
	public String getIT_272() {
		return IT_272;
	}
	public void setIT_272(String iT_272) {
		IT_272 = iT_272;
	}
	public String getIT_279() {
		return IT_279;
	}
	public void setIT_279(String iT_279) {
		IT_279 = iT_279;
	}
	public String getIT_326() {
		return IT_326;
	}
	public void setIT_326(String iT_326) {
		IT_326 = iT_326;
	}
	public String getIT_327() {
		return IT_327;
	}
	public void setIT_327(String iT_327) {
		IT_327 = iT_327;
	}
	public String getIT_328() {
		return IT_328;
	}
	public void setIT_328(String iT_328) {
		IT_328 = iT_328;
	}
	public String getIT_330() {
		return IT_330;
	}
	public void setIT_330(String iT_330) {
		IT_330 = iT_330;
	}
	public String getIT_332() {
		return IT_332;
	}
	public void setIT_332(String iT_332) {
		IT_332 = iT_332;
	}
	public String getIT_340() {
		return IT_340;
	}
	public void setIT_340(String iT_340) {
		IT_340 = iT_340;
	}
	public String getIT_341() {
		return IT_341;
	}
	public void setIT_341(String iT_341) {
		IT_341 = iT_341;
	}
	public String getIT_350() {
		return IT_350;
	}
	public void setIT_350(String iT_350) {
		IT_350 = iT_350;
	}
	public String getIT_351() {
		return IT_351;
	}
	public void setIT_351(String iT_351) {
		IT_351 = iT_351;
	}
	public String getIT_353() {
		return IT_353;
	}
	public void setIT_353(String iT_353) {
		IT_353 = iT_353;
	}
	public String getIT_354() {
		return IT_354;
	}
	public void setIT_354(String iT_354) {
		IT_354 = iT_354;
	}
	public String getIT_356() {
		return IT_356;
	}
	public void setIT_356(String iT_356) {
		IT_356 = iT_356;
	}
	public String getIT_357() {
		return IT_357;
	}
	public void setIT_357(String iT_357) {
		IT_357 = iT_357;
	}
	public String getIT_363() {
		return IT_363;
	}
	public void setIT_363(String iT_363) {
		IT_363 = iT_363;
	}
	public String getIT_367() {
		return IT_367;
	}
	public void setIT_367(String iT_367) {
		IT_367 = iT_367;
	}
	public String getIT_372() {
		return IT_372;
	}
	public void setIT_372(String iT_372) {
		IT_372 = iT_372;
	}
	public String getIT_373() {
		return IT_373;
	}
	public void setIT_373(String iT_373) {
		IT_373 = iT_373;
	}
	public String getIT_375() {
		return IT_375;
	}
	public void setIT_375(String iT_375) {
		IT_375 = iT_375;
	}
	public String getIT_376() {
		return IT_376;
	}
	public void setIT_376(String iT_376) {
		IT_376 = iT_376;
	}
	public String getIT_377() {
		return IT_377;
	}
	public void setIT_377(String iT_377) {
		IT_377 = iT_377;
	}
	public String getIT_378() {
		return IT_378;
	}
	public void setIT_378(String iT_378) {
		IT_378 = iT_378;
	}
	public String getIT_379() {
		return IT_379;
	}
	public void setIT_379(String iT_379) {
		IT_379 = iT_379;
	}
	public String getIT_380() {
		return IT_380;
	}
	public void setIT_380(String iT_380) {
		IT_380 = iT_380;
	}
	public String getIT_381() {
		return IT_381;
	}
	public void setIT_381(String iT_381) {
		IT_381 = iT_381;
	}
	public String getIT_382() {
		return IT_382;
	}
	public void setIT_382(String iT_382) {
		IT_382 = iT_382;
	}
	public String getIT_383() {
		return IT_383;
	}
	public void setIT_383(String iT_383) {
		IT_383 = iT_383;
	}
	public String getIT_384() {
		return IT_384;
	}
	public void setIT_384(String iT_384) {
		IT_384 = iT_384;
	}
	public String getIT_388() {
		return IT_388;
	}
	public void setIT_388(String iT_388) {
		IT_388 = iT_388;
	}
	public String getIT_391() {
		return IT_391;
	}
	public void setIT_391(String iT_391) {
		IT_391 = iT_391;
	}
	public String getIT_432() {
		return IT_432;
	}
	public void setIT_432(String iT_432) {
		IT_432 = iT_432;
	}
	public String getIT_450() {
		return IT_450;
	}
	public void setIT_450(String iT_450) {
		IT_450 = iT_450;
	}
	public String getIT_463() {
		return IT_463;
	}
	public void setIT_463(String iT_463) {
		IT_463 = iT_463;
	}
	public String getIT_467() {
		return IT_467;
	}
	public void setIT_467(String iT_467) {
		IT_467 = iT_467;
	}
	public String getIT_468() {
		return IT_468;
	}
	public void setIT_468(String iT_468) {
		IT_468 = iT_468;
	}
	public String getIT_477() {
		return IT_477;
	}
	public void setIT_477(String iT_477) {
		IT_477 = iT_477;
	}
	public String getIT_478() {
		return IT_478;
	}
	public void setIT_478(String iT_478) {
		IT_478 = iT_478;
	}
	public String getIT_495() {
		return IT_495;
	}
	public void setIT_495(String iT_495) {
		IT_495 = iT_495;
	}
	public String getIT_496() {
		return IT_496;
	}
	public void setIT_496(String iT_496) {
		IT_496 = iT_496;
	}
	public String getIT_497() {
		return IT_497;
	}
	public void setIT_497(String iT_497) {
		IT_497 = iT_497;
	}

    /**
     * @return the courses
     */
    public CoursePreferenceVO getCourses() {
        return courses;
    }

    /**
     * @param courses the courses to set
     */
    public void setCourses(CoursePreferenceVO courses) {
        this.courses = courses;
    }

}
