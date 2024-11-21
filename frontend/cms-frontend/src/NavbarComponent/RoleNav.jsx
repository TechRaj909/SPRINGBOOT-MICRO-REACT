import AdminHeader from "./AdminHeader";
import HeaderCustomer from "./HeaderCustomer";
import NormalHeader from "./NormalHeader";
import SurveyorHeader from "./SurveyorHeader";

const RoleNav = () => {
  const customer = JSON.parse(sessionStorage.getItem("active-customer"));
  const admin = JSON.parse(sessionStorage.getItem("active-admin"));
  const surveyor = JSON.parse(sessionStorage.getItem("active-surveyor"));

  if (admin != null) {
    return <AdminHeader />;
  } else if (customer != null) {
    return <HeaderCustomer />;
  } else if (surveyor != null) {
    return <SurveyorHeader />;
  } else {
    return <NormalHeader />;
  }
};

export default RoleNav;
