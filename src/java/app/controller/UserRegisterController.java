/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOUser;
import app.entity.Registration;
import app.entity.Subject;
import app.entity.User;
import app.utils.GmailService;
import app.utils.RSAEncryption;
import app.utils.RandomSecurePasswordGenerator;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.file.Paths;

/**
 *
 * @author admin
 */
@WebServlet(name = "UserRegisterController", urlPatterns = {"/public/userRegister"})
public class UserRegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        GmailService gmailService = new GmailService(getServletContext());
        DAOUser daoUser = new DAOUser();
        String service = request.getParameter("service");
        String redirectTo = "";
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        if (service == null) {
            service = "";
            redirectTo = "/RegisterErrorNotFound.jsp";
            dispath(request, response, redirectTo);
        }
        if(service.equals("activateRegister")){
            String secure = request.getParameter("secure");
            redirectTo = "/RegisterErrorNotFound.jsp";
            boolean isExist = daoUser.isEmailRegistered(email);
            if (authenData(email, mobile, fullName, gender, secure) && !isExist) {
                User u = new User();
                u.setEmail(email);
                u.setMobile(mobile);
                u.setRoleId(1);
                u.setFullName(fullName);
                RandomSecurePasswordGenerator rSPG;
                rSPG = new RandomSecurePasswordGenerator.PasswordGeneratorBuilder()
                        .useDigits(true)
                        .useLower(true)
                        .useUpper(true)
                        .build();
                String password = rSPG.generateSecureRandomPassword(8);
                u.setPassword(password);
                switch (gender) {
                    case "Male":
                        u.setGenderId(1);
                        break;
                    case "Female":
                        u.setGenderId(2);
                        break;
                    default:
                        u.setGenderId(3);
                        break;
                }
                daoUser.addUser(u);
                request.setAttribute("email", email);
                String emailContent = ""
                        + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n"
                        + " <head>\n"
                        + "  <meta charset=\"UTF-8\">\n"
                        + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n"
                        + "  <meta name=\"x-apple-disable-message-reformatting\">\n"
                        + "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                        + "  <meta content=\"telephone=no\" name=\"format-detection\">\n"
                        + "  <title>New email template 2024-07-06</title><!--[if (mso 16)]>\n"
                        + "    <style type=\"text/css\">\n"
                        + "    a {text-decoration: none;}\n"
                        + "    </style>\n"
                        + "    <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n"
                        + "<xml>\n"
                        + "    <o:OfficeDocumentSettings>\n"
                        + "    <o:AllowPNG></o:AllowPNG>\n"
                        + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                        + "    </o:OfficeDocumentSettings>\n"
                        + "</xml>\n"
                        + "<![endif]--><!--[if !mso]><!-- -->\n"
                        + "  <link href=\"https://fonts.googleapis.com/css2?family=Orbitron&display=swap\" rel=\"stylesheet\"><!--<![endif]-->\n"
                        + "  <style type=\"text/css\">\n"
                        + "#outlook a {\n"
                        + "	padding:0;\n"
                        + "}\n"
                        + ".es-button {\n"
                        + "	mso-style-priority:100!important;\n"
                        + "	text-decoration:none!important;\n"
                        + "}\n"
                        + "a[x-apple-data-detectors] {\n"
                        + "	color:inherit!important;\n"
                        + "	text-decoration:none!important;\n"
                        + "	font-size:inherit!important;\n"
                        + "	font-family:inherit!important;\n"
                        + "	font-weight:inherit!important;\n"
                        + "	line-height:inherit!important;\n"
                        + "}\n"
                        + ".es-desk-hidden {\n"
                        + "	display:none;\n"
                        + "	float:left;\n"
                        + "	overflow:hidden;\n"
                        + "	width:0;\n"
                        + "	max-height:0;\n"
                        + "	line-height:0;\n"
                        + "	mso-hide:all;\n"
                        + "}\n"
                        + ".es-button-border:hover a.es-button, .es-button-border:hover button.es-button {\n"
                        + "	background:#58dfec!important;\n"
                        + "}\n"
                        + ".es-button-border:hover {\n"
                        + "	border-color:#26C6DA #26C6DA #26C6DA #26C6DA!important;\n"
                        + "	background:#58dfec!important;\n"
                        + "	border-style:solid solid solid solid!important;\n"
                        + "}\n"
                        + "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120% } h1 { font-size:30px!important; text-align:center } h2 { font-size:24px!important; text-align:left } h3 { font-size:20px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important; text-align:center } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:24px!important; text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:14px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:18px!important; display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }\n"
                        + "@media screen and (max-width:384px) {.mail-message-content { width:414px!important } }\n"
                        + "</style>\n"
                        + " </head>\n"
                        + " <body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                        + "  <div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"en\" style=\"background-color:#07023C\"><!--[if gte mso 9]>\n"
                        + "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                        + "				<v:fill type=\"tile\" color=\"#07023c\"></v:fill>\n"
                        + "			</v:background>\n"
                        + "		<![endif]-->\n"
                        + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#07023C\">\n"
                        + "     <tr>\n"
                        + "      <td valign=\"top\" style=\"padding:0;Margin:0\">\n"
                        + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                        + "         <tr>\n"
                        + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                        + "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;background-repeat:no-repeat;width:600px;background-image:url(https://fixsugv.stripocdn.email/content/guids/CABINET_0e8fbb6adcc56c06fbd3358455fdeb41/images/vector_0Ia.png);background-position:center center\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\" background=\"https://fixsugv.stripocdn.email/content/guids/CABINET_0e8fbb6adcc56c06fbd3358455fdeb41/images/vector_0Ia.png\" role=\"none\">\n"
                        + "             <tr>\n"
                        + "              <td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:30px;padding-bottom:30px\">\n"
                        + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                        + "                 <tr>\n"
                        + "                  <td class=\"es-m-p0r es-m-p20b\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\">\n"
                        + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"center\" style=\"padding:0;Margin:0\"><h1 style=\"Margin:0;line-height:53px;mso-line-height-rule:exactly;font-family:Orbitron, sans-serif;font-size:44px;font-style:normal;font-weight:bold;color:#10054D\">&nbsp;Welcome to Quiz_Practice</h1></td>\n"
                        + "                     </tr>\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:10px;padding-top:15px;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#26C6DA;font-size:14px\"><img class=\"adapt-img\" src=\"https://fixsugv.stripocdn.email/content/guids/CABINET_dee64413d6f071746857ca8c0f13d696/images/852converted_1x3.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" height=\"300\"></a></td>\n"
                        + "                     </tr>\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">Your account is now ACTIVE! Please use your email and the password below to join us!<br></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">Password: " + password + "</p></td>\n"
                        + "                     </tr>\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#26C6DA;background:#26C6DA;border-width:4px;display:inline-block;border-radius:10px;width:auto\"><a href=\"http://localhost:6969/QuizPractice/home\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#FFFFFF;font-size:20px;padding:10px 25px 10px 30px;display:inline-block;background:#26C6DA;border-radius:10px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:normal;font-style:normal;line-height:24px;width:auto;text-align:center;mso-padding-alt:0;mso-border-alt:10px solid #26C6DA\">To Quiz_Practice</a></span></td>\n"
                        + "                     </tr>\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"center\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px\">You can change your password later! Don't worry about it.</p></td>\n"
                        + "                     </tr>\n"
                        + "                   </table></td>\n"
                        + "                 </tr>\n"
                        + "               </table></td>\n"
                        + "             </tr>\n"
                        + "           </table></td>\n"
                        + "         </tr>\n"
                        + "       </table>\n"
                        + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                        + "         <tr>\n"
                        + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                        + "           <table bgcolor=\"#10054D\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#10054d;width:600px\" role=\"none\">\n"
                        + "             <tr>\n"
                        + "              <td align=\"left\" background=\"https://fixsugv.stripocdn.email/content/guids/CABINET_0e8fbb6adcc56c06fbd3358455fdeb41/images/vector_sSY.png\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:35px;padding-bottom:35px;background-image:url(https://fixsugv.stripocdn.email/content/guids/CABINET_0e8fbb6adcc56c06fbd3358455fdeb41/images/vector_sSY.png);background-repeat:no-repeat;background-position:left center\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:69px\" valign=\"top\"><![endif]-->\n"
                        + "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n"
                        + "                 <tr>\n"
                        + "                  <td class=\"es-m-p20b\" align=\"left\" style=\"padding:0;Margin:0;width:69px\">\n"
                        + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"center\" class=\"es-m-txt-l\" style=\"padding:0;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#26C6DA;font-size:14px\"><img src=\"https://fixsugv.stripocdn.email/content/guids/CABINET_dee64413d6f071746857ca8c0f13d696/images/group_118_lFL.png\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"69\"></a></td>\n"
                        + "                     </tr>\n"
                        + "                   </table></td>\n"
                        + "                 </tr>\n"
                        + "               </table><!--[if mso]></td><td style=\"width:20px\"></td><td style=\"width:471px\" valign=\"top\"><![endif]-->\n"
                        + "               <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n"
                        + "                 <tr>\n"
                        + "                  <td align=\"left\" style=\"padding:0;Margin:0;width:471px\">\n"
                        + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"left\" style=\"padding:0;Margin:0\"><h3 style=\"Margin:0;line-height:34px;mso-line-height-rule:exactly;font-family:Orbitron, sans-serif;font-size:28px;font-style:normal;font-weight:bold;color:#ffffff\"><b>Real people. Here to help.</b></h3></td>\n"
                        + "                     </tr>\n"
                        + "                     <tr>\n"
                        + "                      <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:5px;padding-top:10px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#ffffff;font-size:14px\">Have a question? Give us a call at&nbsp;<strong class=\"text text-primary\"><a href=\"tel:(000)1234567899\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#26C6DA;font-size:14px\">(000) 1234 5678 99</a></strong>&nbsp;to chat with a Customer Success representative.</p></td>\n"
                        + "                     </tr>\n"
                        + "                   </table></td>\n"
                        + "                 </tr>\n"
                        + "               </table><!--[if mso]></td></tr></table><![endif]--></td>\n"
                        + "             </tr>\n"
                        + "           </table></td>\n"
                        + "         </tr>\n"
                        + "       </table></td>\n"
                        + "     </tr>\n"
                        + "   </table>\n"
                        + "  </div>\n"
                        + " </body>\n"
                        + "</html>";
                try {
                    gmailService.sendMailWithContent("User Registered Successfully", emailContent, new String[]{email});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                redirectTo = "/UserRegisterSuccess.jsp";
            }
            dispath(request, response, redirectTo);
        }
        if (service.equals("userRegister")) {
            String message;
            boolean isEmailExist = daoUser.isEmailRegistered(email);
            boolean isMobileExist = daoUser.isMobileRegistered(mobile);
            boolean isExist = (isEmailExist || isMobileExist);
            String gCaptchaService = request.getParameter("gservice");
            if (gCaptchaService == null) {
                gCaptchaService = "notApprove";
            }
            if (isExist) {
                if (isEmailExist && !isMobileExist) {
                    message = "Email address <strong class=\"text text-primary\">" + email + "</strong> has already been registered!";
                } else if (isMobileExist && !isEmailExist) {
                    message = "Phone number <strong class=\"text text-primary\">" + mobile + "</strong> has already been registered!";
                } else {
                    message = "Both email address <strong class=\"text text-primary\">" + email + "</strong> and phone number <strong class=\"text text-primary\">" + mobile + "</strong> have already been registered!";
                }
            } else {
                if (gCaptchaService.equals("notApprove")) {
                    message = "Please check the captcha below!";
                    String cap = "checkCaptcha";
                    request.setAttribute("email", email);
                    request.setAttribute("mobile", mobile);
                    request.setAttribute("gender", gender);
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("captcha", cap);
                } else {
                    message = "We have sent you an email! Please check your inbox.";
                    String reachURL = buildRegisterUrl(email, mobile, fullName, gender);
                    String emailbody = ""
                            + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                            + "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n"
                            + " <head>\n"
                            + "  <meta charset=\"UTF-8\">\n"
                            + "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n"
                            + "  <meta name=\"x-apple-disable-message-reformatting\">\n"
                            + "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                            + "  <meta content=\"telephone=no\" name=\"format-detection\">\n"
                            + "  <title>New Message</title><!--[if (mso 16)]>\n"
                            + "    <style type=\"text/css\">\n"
                            + "    a {text-decoration: none;}\n"
                            + "    </style>\n"
                            + "    <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>\n"
                            + "<xml>\n"
                            + "    <o:OfficeDocumentSettings>\n"
                            + "    <o:AllowPNG></o:AllowPNG>\n"
                            + "    <o:PixelsPerInch>96</o:PixelsPerInch>\n"
                            + "    </o:OfficeDocumentSettings>\n"
                            + "</xml>\n"
                            + "<![endif]--><!--[if !mso]><!-- -->\n"
                            + "  <link href=\"https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i\" rel=\"stylesheet\"><!--<![endif]-->\n"
                            + "  <style type=\"text/css\">\n"
                            + "#outlook a {\n"
                            + "	padding:0;\n"
                            + "}\n"
                            + ".es-button {\n"
                            + "	mso-style-priority:100!important;\n"
                            + "	text-decoration:none!important;\n"
                            + "}\n"
                            + "a[x-apple-data-detectors] {\n"
                            + "	color:inherit!important;\n"
                            + "	text-decoration:none!important;\n"
                            + "	font-size:inherit!important;\n"
                            + "	font-family:inherit!important;\n"
                            + "	font-weight:inherit!important;\n"
                            + "	line-height:inherit!important;\n"
                            + "}\n"
                            + ".es-desk-hidden {\n"
                            + "	display:none;\n"
                            + "	float:left;\n"
                            + "	overflow:hidden;\n"
                            + "	width:0;\n"
                            + "	max-height:0;\n"
                            + "	line-height:0;\n"
                            + "	mso-hide:all;\n"
                            + "}\n"
                            + "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120% } h1 { font-size:30px!important; text-align:left } h2 { font-size:60px!important; text-align:left } h3 { font-size:20px!important; text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important; text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:60px!important; text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important; text-align:center } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button, button.es-button { font-size:20px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0!important } .es-m-p0r { padding-right:0!important } .es-m-p0l { padding-left:0!important } .es-m-p0t { padding-top:0!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-m-p5 { padding:5px!important } .es-m-p5t { padding-top:5px!important } .es-m-p5b { padding-bottom:5px!important } .es-m-p5r { padding-right:5px!important } .es-m-p5l { padding-left:5px!important } .es-m-p10 { padding:10px!important } .es-m-p10t { padding-top:10px!important } .es-m-p10b { padding-bottom:10px!important } .es-m-p10r { padding-right:10px!important } .es-m-p10l { padding-left:10px!important } .es-m-p15 { padding:15px!important } .es-m-p15t { padding-top:15px!important } .es-m-p15b { padding-bottom:15px!important } .es-m-p15r { padding-right:15px!important } .es-m-p15l { padding-left:15px!important } .es-m-p20 { padding:20px!important } .es-m-p20t { padding-top:20px!important } .es-m-p20r { padding-right:20px!important } .es-m-p20l { padding-left:20px!important } .es-m-p25 { padding:25px!important } .es-m-p25t { padding-top:25px!important } .es-m-p25b { padding-bottom:25px!important } .es-m-p25r { padding-right:25px!important } .es-m-p25l { padding-left:25px!important } .es-m-p30 { padding:30px!important } .es-m-p30t { padding-top:30px!important } .es-m-p30b { padding-bottom:30px!important } .es-m-p30r { padding-right:30px!important } .es-m-p30l { padding-left:30px!important } .es-m-p35 { padding:35px!important } .es-m-p35t { padding-top:35px!important } .es-m-p35b { padding-bottom:35px!important } .es-m-p35r { padding-right:35px!important } .es-m-p35l { padding-left:35px!important } .es-m-p40 { padding:40px!important } .es-m-p40t { padding-top:40px!important } .es-m-p40b { padding-bottom:40px!important } .es-m-p40r { padding-right:40px!important } .es-m-p40l { padding-left:40px!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }\n"
                            + "@media screen and (max-width:384px) {.mail-message-content { width:414px!important } }\n"
                            + "</style>\n"
                            + " </head>\n"
                            + " <body style=\"width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n"
                            + "  <div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"en\" style=\"background-color:#F6F6F6\"><!--[if gte mso 9]>\n"
                            + "			<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n"
                            + "				<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n"
                            + "			</v:background>\n"
                            + "		<![endif]-->\n"
                            + "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#F6F6F6\">\n"
                            + "     <tr>\n"
                            + "      <td valign=\"top\" style=\"padding:0;Margin:0\">\n"
                            + "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                            + "         <tr>\n"
                            + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                            + "           <table class=\"es-content-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#D5F2FF;width:600px\">\n"
                            + "             <tr>\n"
                            + "              <td align=\"left\" style=\"padding:0;Margin:0\">\n"
                            + "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                 <tr>\n"
                            + "                  <td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n"
                            + "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                     <tr>\n"
                            + "                      <td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#666666;font-size:14px\"><br></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#666666;font-size:14px\"><br></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#666666;font-size:14px\">QUIZ PRACTICE</p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#666666;font-size:14px\"><br></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#666666;font-size:14px\"><br></p></td>\n"
                            + "                     </tr>\n"
                            + "                     <tr>\n"
                            + "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><img class=\"adapt-img\" src=\"https://fixsugv.stripocdn.email/content/guids/CABINET_42ee42c3323cd2870f5d668cc97bca8d9c0c672a85caed355ddf93ecfe946767/images/sub20.jpg\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"600\"></td>\n"
                            + "                     </tr>\n"
                            + "                   </table></td>\n"
                            + "                 </tr>\n"
                            + "               </table></td>\n"
                            + "             </tr>\n"
                            + "           </table></td>\n"
                            + "         </tr>\n"
                            + "       </table>\n"
                            + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n"
                            + "         <tr>\n"
                            + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                            + "           <table bgcolor=\"#d5f2ff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#D5F2FF;width:600px\">\n"
                            + "             <tr>\n"
                            + "              <td align=\"left\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-top:40px\">\n"
                            + "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                 <tr>\n"
                            + "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:560px\">\n"
                            + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                     <tr>\n"
                            + "                      <td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#999999;font-size:14px\">HELLO " + fullName + "!</p></td>\n"
                            + "                     </tr>\n"
                            + "                     <tr>\n"
                            + "                      <td align=\"center\" class=\"es-m-txt-c\" style=\"padding:0;Margin:0;padding-top:10px;padding-bottom:10px\"><h1 style=\"Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:'playfair display', georgia, 'times new roman', serif;font-size:30px;font-style:normal;font-weight:normal;color:#2C3F58\">User Register Request</h1></td>\n"
                            + "                     <tr>\n"
                            + "<tr align=\"center\" class=\"esd-block-text es-p5t es-p5b es-p40r es-p40l es-m-p0r es-m-p0l\">\n"
                            + "    <p>&nbsp;To active your account, please click on the activate button!</p>\n"
                            + "</tr>"
                            + "                      <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                            + "                        <span class=\"msohide es-button-border\" "
                            + "style=\"border-style:solid;border-color:#2CB543;"
                            + "background:#55f646;border-width:0px;display:inline-block;"
                            + "border-radius:30px;width:auto;mso-hide:all\">"
                            + "<a href=\"" + generateRegisterUrl(request, reachURL) + "\" class=\"es-button\" target=\"_blank\" "
                            + "style=\"mso-style-priority:100 !important;text-decoration:none;"
                            + "-webkit-text-size-adjust:none;-ms-text-size-adjust:none;"
                            + "mso-line-height-rule:exactly;color:#ffffff;font-size:18px;"
                            + "display:inline-block;background:#55f646;border-radius:30px;"
                            + "font-family:Lexend, Arial, sans-serif;font-weight:normal;"
                            + "font-style:normal;line-height:22px;width:auto;text-align:center;"
                            + "padding:10px 30px 10px 30px;mso-padding-alt:0;mso-border-alt:10px solid #55f646\">"
                            + " Activate Now </a>"
                            + "</span><!--<![endif]--></td>\n"
                            + "                     </tr>\n"
                            + "                   </table></td>\n"
                            + "                 </tr>\n"
                            + "               </table></td>\n"
                            + "             </tr>\n"
                            + "             <tr>\n"
                            + "              <td align=\"left\" style=\"padding:0;Margin:0;padding-bottom:30px\">\n"
                            + "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                 <tr>\n"
                            + "                  <td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;width:600px\">\n"
                            + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                     <tr>\n"
                            + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\n"
                            + "                     </tr>\n"
                            + "                   </table></td>\n"
                            + "                 </tr>\n"
                            + "               </table></td>\n"
                            + "             </tr>\n"
                            + "           </table></td>\n"
                            + "         </tr>\n"
                            + "       </table>\n"
                            + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n"
                            + "         <tr>\n"
                            + "          <td align=\"center\" style=\"padding:0;Margin:0\">\n"
                            + "           <table class=\"es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:600px\" role=\"none\">\n"
                            + "             <tr>\n"
                            + "              <td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\">\n"
                            + "               <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                 <tr>\n"
                            + "                  <td align=\"left\" style=\"padding:0;Margin:0;width:560px\">\n"
                            + "                   <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n"
                            + "                     <tr>\n"
                            + "                      <td align=\"center\" style=\"padding:0;Margin:0;display:none\"></td>\n"
                            + "                     </tr>\n"
                            + "                   </table></td>\n"
                            + "                 </tr>\n"
                            + "               </table></td>\n"
                            + "             </tr>\n"
                            + "           </table></td>\n"
                            + "         </tr>\n"
                            + "       </table></td>\n"
                            + "     </tr>\n"
                            + "   </table>\n"
                            + "  </div>\n"
                            + " </body>\n"
                            + "</html>";
                    try {
                        gmailService.sendMailWithContent("Quiz_Practice User Register", emailbody, new String[]{email});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            request.setAttribute("message", message);
            request.setAttribute("isExist", isExist);
            redirectTo = "/UserRegisterValidate.jsp";
            dispath(request, response, redirectTo);
        }
    }

    private String fixData(String input) {
        input = input.trim();
        input = input.replaceAll(" ", "+");
        return input;
    }

    private boolean authenData(String email, String mobile, String fullName, String gender, String secure) {
        RSAEncryption rsa = new RSAEncryption();
        String path = String.valueOf(Paths.get("E:\\summer2024-swp391.se1830-g6\\web\\private\\privateKey.rsa"));
        String initData = email + mobile + fullName + gender;
        //there's a big bug that the request remove my plus (+) by a space ( )
        //I fixed it, wasted 1 hour trying to figure out what happen
        secure = fixData(secure);
        String truthData = rsa.RSAdecryption(secure, path);
        return initData.equals(truthData);
    }

    private String buildRegisterUrl(String email, String mobile, String fullName, String gender) {
        RSAEncryption rsa = new RSAEncryption();
        String toUrl = "service=activateRegister";
        String path = String.valueOf(Paths.get("E:\\summer2024-swp391.se1830-g6\\web\\private\\publicKey.rsa"));
        String initData = email + mobile + fullName + gender;
        String add;
        add = "&email=" + email;
        toUrl += add;
        add = "&mobile=" + mobile;
        toUrl += add;
        add = "&fullName=" + fullName;
        toUrl += add;
        add = "&gender=" + gender;
        toUrl += add;
        add = "&secure=" + rsa.RSAencryption(initData, path);
        toUrl += add;
        return toUrl;
    }

    private String generateRegisterUrl(HttpServletRequest req, String token) {
        return String.format(
                "%s://%s:%s%s/public/userRegister?" + token,
                req.getScheme(),
                req.getServerName(),
                req.getServerPort(),
                req.getContextPath()
        );
    }

    public void dispath(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        RequestDispatcher dispth = request.getRequestDispatcher(url);
        //run, show
        dispth.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
