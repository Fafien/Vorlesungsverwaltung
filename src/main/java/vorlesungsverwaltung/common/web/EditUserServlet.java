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
import javax.servlet.RequestDispatcher;
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

        //Daten für den Benutzer müssen nur geholt werden
        FormValues form = (FormValues) session.getAttribute("edit_form");
        if (form == null || form.getErrors().isEmpty()) {
            form = this.getFormValuesForCurrentUser(currentUser);
            session.setAttribute("edit_form", form);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit/editUser.jsp");
        dispatcher.forward(request, response);
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
        String courseName = request.getParameter("coursename");

        User user = this.userBean.findById(username);
        List<String> errors = new ArrayList<>();

        if (!user.checkPassword(oldPassword)) {
            errors.add("Die Eingabe Ihres aktuellen Passworts ist leider falsch!");
        }
        if (!newPassword.equals("") && !newPasswordConfirm.equals("")) {
            if (!newPassword.equals(newPasswordConfirm)) {
                errors.add("Ihre beiden neuen Passwörter stimmen nicht überein.");
            } else {
                user.setPassword(newPassword);
            }
        }
        if (courseName != null && !courseName.trim().equals("")) {
            Course course = this.courseBean.findByCourseName(courseName);
            user.setCourse(course);
        } else {
            user.setCourse(null);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
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
        Course course = currentUser.getCourse();
        String courseName = "";

        if (course != null) {
            courseName = course.getCourseName();
        }

        Map<String, String[]> formValues = new HashMap<>();
        formValues.put("username", new String[]{currentUser.getUsername()});
        formValues.put("firstname", new String[]{currentUser.getFirstName()});
        formValues.put("lastname", new String[]{currentUser.getLastName()});
        formValues.put("currentCourseName", new String[]{courseName});

        form.setValues(formValues);

        return form;
    }
}
