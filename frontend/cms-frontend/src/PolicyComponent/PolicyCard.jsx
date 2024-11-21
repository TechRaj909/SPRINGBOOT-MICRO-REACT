import { Link } from "react-router-dom";

const PolicyCard = (policy) => {
  return (
    <div className="col">
      <Link
        to={`/policy/${policy.item.id}/detail`}
        className="card car-card h-100"
        style={{ textDecoration: "none" }}
      >
        <div className="col-md-8">
          <div className="card-body text-color">
            <h4>Policy Name: {policy.item.name}</h4>
            <h5 className="header-logo-color">
              Description: {policy.item.description}
            </h5>
            <h5 className="header-logo-color">Plan: {policy.item.plan}</h5>
            <h5 className="header-logo-color">
              Premium Amount: Rs. {policy.item.premiumAmount}
            </h5>
          </div>
        </div>
      </Link>
    </div>
  );
};

export default PolicyCard;
