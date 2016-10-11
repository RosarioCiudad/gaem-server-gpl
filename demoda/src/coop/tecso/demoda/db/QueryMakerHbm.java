package coop.tecso.demoda.db;


class QueryMakerHbm extends QueryMakerBase implements QueryMaker {
	
	org.hibernate.Session session;
	
	QueryMakerHbm(org.hibernate.Session s) {
		session = s;
	}
	
	QueryMakerHbm(org.hibernate.classic.Session s) {
		session = s;
	}

	public QueryMakerHbm() {
	}
}
