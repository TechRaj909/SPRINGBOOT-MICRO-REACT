import { Routes, Route } from "react-router-dom";
import Header from "./NavbarComponent/Header";
import AdminRegisterForm from "./UserComponent/AdminRegisterForm";
import UserLoginForm from "./UserComponent/UserLoginForm";
import UserRegister from "./UserComponent/UserRegister";
import AboutUs from "./PageComponent/AboutUs";
import ContactUs from "./PageComponent/ContactUs";
import HomePage from "./PageComponent/HomePage";
import ViewAllCustomers from "./UserComponent/ViewAllCustomers";
import ViewAllSurveyors from "./UserComponent/ViewAllSurveyors";
import AddPolicyForm from "./PolicyComponent/AddPolicyForm";
import PolicyDetail from "./PolicyComponent/PolicyDetail";
import ViewPolicyApplication from "./PolicyComponent/ViewPolicyApplication";
import AddClaim from "./ClaimComponent/AddClaim";
import ViewMyClaims from "./ClaimComponent/ViewMyClaims";
import ViewAllClaims from "./ClaimComponent/ViewAllClaims";
import ViewAllPolicies from "./PolicyComponent/ViewAllPolicies";
import ViewAllPolicyApplication from "./PolicyComponent/ViewAllPolicyApplication";
import ViewSurveyorClaims from "./ClaimComponent/ViewSurveyorClaims";

function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/user/admin/register" element={<AdminRegisterForm />} />
        <Route path="/user/login" element={<UserLoginForm />} />
        <Route path="/user/customer/register" element={<UserRegister />} />
        <Route path="/user/surveyor/register" element={<UserRegister />} />
        <Route path="/user/irda/register" element={<UserRegister />} />
        <Route path="/aboutus" element={<AboutUs />} />
        <Route path="/contactus" element={<ContactUs />} />
        <Route path="/admin/customer/all" element={<ViewAllCustomers />} />
        <Route path="/admin/surveyor/all" element={<ViewAllSurveyors />} />
        <Route path="/admin/policy/add" element={<AddPolicyForm />} />
        <Route path="/policy/:policyId/detail" element={<PolicyDetail />} />
        <Route
          path="/customer/policy/application/view"
          element={<ViewPolicyApplication />}
        />
        <Route path="/customer/policy/claim" element={<AddClaim />} />
        <Route path="/customer/policy/claim/view" element={<ViewMyClaims />} />
        <Route path="/admin/policy/claim/view" element={<ViewAllClaims />} />
        <Route path="/admin/policy/view/all" element={<ViewAllPolicies />} />
        <Route
          path="/admin/customer/policy/application/all"
          element={<ViewAllPolicyApplication />}
        />
        <Route
          path="/surveyor/policy/customer/claims"
          element={<ViewSurveyorClaims />}
        />
      </Routes>
    </div>
  );
}

export default App;
