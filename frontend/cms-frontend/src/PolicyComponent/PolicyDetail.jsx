import { useState, useEffect } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";
import axios from "axios";

const PolicyDetail = () => {
  let navigate = useNavigate();

  const { policyId } = useParams();

  const customer = JSON.parse(sessionStorage.getItem("active-customer"));

  const [policy, setPolicy] = useState({});

  const retrievePolicy = async () => {
    const response = await axios.get(
      "http://localhost:9000/api/policy/fetch?policyId=" + policyId
    );
    return response.data;
  };

  useEffect(() => {
    const getAllPolicy = async () => {
      const res = await retrievePolicy();
      if (res) {
        setPolicy(res.policies[0]);
      }
    };
    getAllPolicy();
  }, []);

  const applyPolicy = (policyId) => {
    if (!customer) {
      alert("Please login as Customer");
    } else {
      fetch("http://localhost:9000/api/policy/application/add", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          customerId: customer.id,
          policyId: policyId,
        }),
      })
        .then((result) => {
          console.log("result", result);
          result.json().then((res) => {
            if (res.success) {
              toast.success(res.responseMessage, {
                position: "top-center",
                autoClose: 1000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });

              setTimeout(() => {
                navigate("/home");
              }, 2000); // Redirect after 3 seconds
            } else {
              toast.error(res.responseMessage, {
                position: "top-center",
                autoClose: 1000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
            }
          });
        })
        .catch((error) => {
          console.error(error);
          toast.error("It seems server is down", {
            position: "top-center",
            autoClose: 1000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        });
    }
  };

  return (
    <div>
      <div className="mt-2 d-flex aligns-items-center justify-content-center">
        <div className="row">
          <div className="col">
            <div className="form-card" style={{ width: "25rem" }}>
              <div className="container-fluid">
                <div
                  className="card-header bg-color custom-bg-text mt-2 d-flex justify-content-center align-items-center"
                  style={{
                    borderRadius: "1em",
                    height: "38px",
                  }}
                >
                  <h4 className="card-title">Policy Details</h4>
                </div>
                <div className="card-body mt-3">
                  <h4 className="text-color">Policy Name: {policy.name}</h4>
                  <h4 className="text-color">Policy ID: {policy.policyId}</h4>
                  <h5 className="header-logo-color">
                    Description: {policy.description}
                  </h5>
                  <h5 className="header-logo-color">Plan: {policy.plan}</h5>
                  <h5 className="header-logo-color">
                    Premium Amount: Rs. {policy.premiumAmount}
                  </h5>
                </div>
                <div className="card-footer mt-2 d-flex justify-content-center align-items-center">
                  <button
                    onClick={() => applyPolicy(policy.id)}
                    className="btn btn-lg bg-color custom-bg-text"
                  >
                    <b>Apply</b>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <div className="col">
            <div className="form-card" style={{ width: "40rem" }}>
              <div className="container-fluid">
                <div
                  className="card-header bg-color custom-bg-text mt-2 d-flex justify-content-center align-items-center"
                  style={{
                    borderRadius: "1em",
                    height: "38px",
                  }}
                >
                  <h4 className="card-title">Coverage Details</h4>
                </div>
                <div className="card-body mt-3">
                  <div className="table-responsive">
                    <table className="table table-hover text-color text-center">
                      <thead className="table-bordered border-color bg-color custom-bg-text">
                        <tr>
                          <th scope="col">Policy Type</th>
                          <th scope="col">Policy Description</th>
                          <th scope="col">Coverage Amount</th>
                        </tr>
                      </thead>
                      <tbody>
                        {policy.coverageDetailsList &&
                          policy.coverageDetailsList.map((coverage) => {
                            return (
                              <tr>
                                <td>
                                  <b>{coverage.type}</b>
                                </td>
                                <td>
                                  <b>{coverage.description}</b>
                                </td>
                                <td>
                                  <b>{coverage.amount}</b>
                                </td>
                              </tr>
                            );
                          })}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PolicyDetail;
