package com.td.mace.dao;

import com.td.mace.model.User;
import com.td.mace.model.UserAttendance;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userAttendanceDao")
public class UserAttendanceDaoImpl extends AbstractDao<Integer, UserAttendance>
		implements UserAttendanceDao {

	static final Logger logger = LoggerFactory
			.getLogger(UserAttendanceDaoImpl.class);

	public UserAttendance findById(int id) {
		UserAttendance userAttendance = getByKey(id);

		if (userAttendance != null) {
			Hibernate.initialize(userAttendance.getUser());
		}
		return userAttendance;
	}

	/* Need to work on this */
	public UserAttendance findBySSOId(String ssoId) {
		logger.info("ssoId : {}", ssoId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", ssoId));
		UserAttendance userAttendance = (UserAttendance) crit.uniqueResult();

		if (userAttendance != null) {
			Hibernate.initialize(userAttendance.getUser());
		}
		return userAttendance;
	}

	/* Need to work on this */
	@Override
	public UserAttendance findByUserId(String userId) {
		logger.info("userId : {}", userId);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("userId", userId));
		UserAttendance userAttendance = (UserAttendance) crit.uniqueResult();

		if (userAttendance != null) {
			Hibernate.initialize(userAttendance.getUser());
		}
		return userAttendance;
	}

	@SuppressWarnings("unchecked")
	public List<UserAttendance> findAllUserAttendances() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("user.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To
		// avoid
		// duplicates.
		List<UserAttendance> userAttendances = (List<UserAttendance>) criteria
				.list();

		// No need to fetch userAttendanceProfiles since we are not showing them
		// on
		// list page. Let them lazy load.
		// Uncomment below lines for eagerly fetching of userAttendanceProfiles
		// if
		// you want.

		for (UserAttendance userAttendance : userAttendances) {
			Hibernate.initialize(userAttendance.getUser());
		}

		return userAttendances;
	}

	@SuppressWarnings("unchecked")
	public List<UserAttendance> findAllUserAttendancesBySSOId(String ssoId) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// criteria.add(Restrictions.eq("ssoId", ssoId));
		criteria.addOrder(Order.asc("user.id"));
		List<UserAttendance> userAttendances = (List<UserAttendance>) criteria
				.list();
		List<UserAttendance> allowedUserAttendances = new ArrayList<UserAttendance>();

		for (UserAttendance userAttendance : userAttendances) {
			Hibernate.initialize(userAttendance.getUser());
			User user = userAttendance.getUser();
			if (user.getSsoId().equalsIgnoreCase(ssoId)) {
				allowedUserAttendances.add(userAttendance);
			}
		}
		return allowedUserAttendances;
	}

	public void save(UserAttendance userAttendance) {
		persist(userAttendance);
	}

	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		UserAttendance userAttendance = (UserAttendance) crit.uniqueResult();
		delete(userAttendance);
	}

	/* Need to work on this */
	public void deleteByUserId(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("userId", id));
		UserAttendance userAttendance = (UserAttendance) crit.uniqueResult();
		delete(userAttendance);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAttendance> findAllUserAttendancesUpdated() {
		//Query query = getSession().createSQLQuery("SELECT aua.id,aua.user_id,aua.mJan-SUM(wpua.mJan) AS mJan,aua.mFeb-SUM(wpua.mFeb) AS mFeb,aua.mMar-SUM(wpua.mMar) AS mMar,aua.mApr-SUM(wpua.mApr) AS mApr,aua.mMay-SUM(wpua.mMay) AS mMay,aua.mJun-SUM(wpua.mJun) AS mJun,aua.mJul-SUM(wpua.mJul) AS mJul,aua.mAug-SUM(wpua.mAug) AS mAug,aua.mSep-SUM(wpua.mSep) AS mSep,aua.mOct-SUM(wpua.mOct) AS mOct,aua.mNov-SUM(wpua.mNov) AS mNov,aua.mDec-SUM(wpua.mDec) AS mDec,aua.year_name FROM WORK_PACKAGE_app_user_ALLOCATIONS wpua JOIN app_user_attendance aua ON wpua.user_id = aua.user_id WHERE aua.user_id=:user_id GROUP BY user_id").addEntity(UserAttendance.class);
		/*Query query = getSession().createSQLQuery("SELECT aua.id,aua.user_id,IFNULL(aua.mJan-(IFNULL(SUM(wpua.mJan),0.00)),0.00) AS mJan,IFNULL(aua.mFeb-(IFNULL(SUM(wpua.mFeb),0.00)),0.00) AS mFeb,IFNULL(aua.mMar-(IFNULL(SUM(wpua.mMar),0.00)),0.00) AS mMar,IFNULL(aua.mApr-(IFNULL(SUM(wpua.mApr),0.00)),0.00) AS mApr,IFNULL(aua.mMay-(IFNULL(SUM(wpua.mMay),0.00)),0.00) AS mMay,IFNULL(aua.mJun-(IFNULL(SUM(wpua.mJun),0.00)),0.00) AS mJun,IFNULL(aua.mJul-(IFNULL(SUM(wpua.mJul),0.00)),0.00) AS mJul,IFNULL(aua.mAug-(IFNULL(SUM(wpua.mAug),0.00)),0.00) AS mAug,IFNULL(aua.mSep-(IFNULL(SUM(wpua.mSep),0.00)),0.00) AS mSep,IFNULL(aua.mOct-(IFNULL(SUM(wpua.mOct),0.00)),0.00) AS mOct,IFNULL(aua.mNov-(IFNULL(SUM(wpua.mNov),0.00)),0.00) AS mNov,IFNULL(aua.mDec-(IFNULL(SUM(wpua.mDec),0.00)),0.00) AS mDec,aua.year_name as Year_Name FROM app_user_attendance aua LEFT JOIN WORK_PACKAGE_app_user_ALLOCATIONS wpua  ON  aua.user_id = wpua.user_id GROUP BY aua.user_id").addEntity(UserAttendance.class);*/
		//Query query = getSession().createSQLQuery("SELECT aua.id,aua.user_id,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mJan - (SUM(wpua.mJan))),aua.mJan),0.00) AS mJan,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mFeb - (SUM(wpua.mFeb))),aua.mFeb),0.00) AS mFeb,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mMar - (SUM(wpua.mMar))),aua.mMar),0.00) AS mMar,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mApr - (SUM(wpua.mApr))),aua.mApr),0.00) AS mApr,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mMay - (SUM(wpua.mMay))),aua.mMay),0.00) AS mMay,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mJun - (SUM(wpua.mJun))),aua.mJun),0.00) AS mJun,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mJul - (SUM(wpua.mJul))),aua.mJul),0.00) AS mJul,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mAug - (SUM(wpua.mAug))),aua.mAug),0.00) AS mAug,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mSep - (SUM(wpua.mSep))),aua.mSep),0.00) AS mSep,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mOct - (SUM(wpua.mOct))),aua.mOct),0.00) AS mOct,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mNov - (SUM(wpua.mNov))),aua.mNov),0.00) AS mNov,IFNULL(IF(aua.YEAR_NAME= wpua.YEAR_NAME AND aua.user_id = wpua.user_id, (aua.mDec - (SUM(wpua.mDec))),aua.mDec),0.00) AS mDec,aua.year_name AS Year_Name FROM app_user_attendance aua LEFT JOIN WORK_PACKAGE_app_user_ALLOCATIONS wpua  ON  aua.user_id = wpua.user_id GROUP BY aua.user_id,aua.YEAR_NAME").addEntity(UserAttendance.class);
		Query query = getCurrentSession().createSQLQuery("select  aua.id, aua.user_id,  ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mjan - sum(wpua.mjan),      aua.mjan    ),    0.00  ) as mjan,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mfeb - sum(wpua.mfeb),      aua.mfeb    ),    0.00  ) as mfeb,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mmar - sum(wpua.mmar),      aua.mmar    ),    0.00  ) as mmar,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mapr - sum(wpua.mapr),      aua.mapr    ),    0.00  ) as mapr,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mmay - sum(wpua.mmay),      aua.mmay    ),    0.00  ) as mmay,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mjun - sum(wpua.mjun),      aua.mjun    ),    0.00  ) as mjun,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mjul - sum(wpua.mjul),      aua.mjul    ),    0.00  ) as mjul,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.maug - sum(wpua.maug),      aua.maug    ),    0.00  ) as maug,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.msep - sum(wpua.msep),      aua.msep    ),    0.00  ) as msep,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.moct - sum(wpua.moct),      aua.moct    ),    0.00  ) as moct,    ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mnov - sum(wpua.mnov),      aua.mnov    ),    0.00  ) as mnov,  ifnull(    if(      aua.year_name in      (select        wpuasub.year_name      from        work_package_app_user_allocations wpuasub,        app_user_attendance auasub      where auasub.user_id = wpuasub.user_id        and wpuasub.year_name = auasub.year_name)      and aua.user_id = wpua.user_id,      aua.mdec - sum(wpua.mdec),      aua.mdec    ),    0.00  ) as mdec,  aua.year_name as year_name from  app_user_attendance aua  left join work_package_app_user_allocations wpua    on aua.user_id = wpua.user_id and aua.year_name = wpua.year_name     LEFT JOIN app_user ON aua.user_id = app_user.id where app_user.active = true group by aua.user_id,  aua.year_name").addEntity(UserAttendance.class);
		//query.setParameter("user_id", userId);
		List<UserAttendance> userAttendances = query.list();
		return userAttendances;
	}

}
