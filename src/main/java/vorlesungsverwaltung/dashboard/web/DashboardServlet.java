
package vorlesungsverwaltung.dashboard.web;

import vorlesungsverwaltung.dashboard.ejb.DashboardContentProvider;
import vorlesungsverwaltung.dashboard.ejb.DashboardSection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/app/dashboard/"})
public class DashboardServlet extends HttpServlet {

    @EJB(beanName = "lectures")
    DashboardContentProvider lectureContent;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<DashboardSection> sections = new ArrayList<>();
        request.setAttribute("sections", sections);

        lectureContent.createDashboardContent(sections);

        request.getRequestDispatcher("/WEB-INF/dashboard/dashboard.jsp").forward(request, response);
    }

}
