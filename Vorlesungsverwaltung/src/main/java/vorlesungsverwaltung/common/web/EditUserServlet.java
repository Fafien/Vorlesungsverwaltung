/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vorlesungsverwaltung.common.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vorlesungsverwaltung.common.ejb.UserBean;
import vorlesungsverwaltung.common.ejb.ValidationBean;
import vorlesungsverwaltung.common.jpa.User;
import vorlesungsverwaltung.lectures.ejb.CourseBean;
import vorlesungsverwaltung.lectures.jpa.Course;

/**
 *
 * @author Licht
 */
@WebServlet(name = "EditUserServlet", urlPatterns = {"/app/user/edit/"})
public class EditUserServlet extends HttpServlet {

    @EJB
    private UserBean userBean;

    @EJB
    private ValidationBean validationBean;

    @EJB
    private CourseBean courseBean;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User currentUser = this.userBean.getCurrentUser();
        HttpSession session = request.getSession();

        session.setAttribute("courses", this.courseBean.findAll());
        FormValues form = this.getFormValuesForCurrentUser(currentUser);
        session.setAttribute("edit_form", form);
        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/user/editUser.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");
        String oldPassword = request.getParameter("oldPassword");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String courseName = request.getParameter("course");
        Course course = this.courseBean.findByCourseName(courseName);

        User user = this.userBean.findById(username);
        List<String> errors = new ArrayList<>();

        //TOOD: altes Passwort muss vor dem Vergleich gehashed werden
        if (!oldPassword.equals(user.getPassword())) {
            errors.add("Das alte Passwort stimmt nicht mit Ihrem aktuellen Passwort überein");
        }
        if (newPassword != null && newPasswordConfirm != null && !newPassword.equals(newPasswordConfirm)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }

        user.setPassword(newPasswordConfirm);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCourse(course);
        errors = validationBean.validate(user, errors);

        if (errors.isEmpty()) {
            this.userBean.update(user);
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            FormValues form = new FormValues();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            HttpSession session = request.getSession();
            session.setAttribute("edit_form", form);
            response.sendRedirect(request.getRequestURI());
        }
    }

    private FormValues getFormValuesForCurrentUser(User currentUser) {
        FormValues form = new FormValues();

        Map<String, String[]> formValues = new HashMap<>();
        formValues.put("username", new String[]{currentUser.getUsername()});
        formValues.put("firstname", new String[]{currentUser.getFirstName()});
        formValues.put("lastname", new String[]{currentUser.getLastName()});
        formValues.put("currentCourseName", new String[]{currentUser.getCourse().getCourseName()});

        form.setValues(formValues);

        return form;
    }
}
