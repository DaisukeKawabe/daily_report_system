package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;
/**
 * Servlet implementation class ReportsLikeServet
 */
@WebServlet("/reports/like")
public class ReportsLikeServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLikeServet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	EntityManager em = DBUtil.createEntityManager();
    	Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
    	// いいね数を1足す
    	int yoineCnt = 1;
    	if (r.getLike_count() != null) {
    	    yoineCnt = r.getLike_count().intValue() + 1;
    	}
    	// 更新情報をセット
    	r.setLike_count(Integer.valueOf(yoineCnt));
    	r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
    	// テーブル更新
    	em.getTransaction().begin();
    	em.getTransaction().commit();
    	em.close();
    	request.getSession().setAttribute("flush", "いいねしました。");
    	response.sendRedirect(request.getContextPath() + "/reports/index");
    }
}
