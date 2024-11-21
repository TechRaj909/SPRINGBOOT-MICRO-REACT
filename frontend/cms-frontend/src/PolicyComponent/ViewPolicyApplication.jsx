import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";

const ViewPolicyApplication = () => {
  let navigate = useNavigate();
  const customer = JSON.parse(sessionStorage.getItem("active-customer"));

  const [applications, setApplications] = useState([]);
  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  useEffect(() => {
    const getApplication = async () => {
      const application = await retrieveApplication();
      if (application) {
        setApplications(application.applications);
      }
    };

    getApplication();
  }, []);

  const retrieveApplication = async () => {
    const response = await axios.get(
      "http://localhost:9000/api/policy/application/fetch/customer-wise?customerId=" +
        customer.id,
      {
        headers: {
          //   Authorization: "Bearer " + admin_jwtToken, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const claimPolicy = (application) => {
    navigate("/customer/policy/claim", { state: application });
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 custom-bg"
        style={{
          height: "45rem",
        }}
      >
        <div
          className="card-header custom-bg-text text-center bg-color"
          style={{
            borderRadius: "1em",
            height: "50px",
          }}
        >
          <h2>All Applications</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive">
            <table className="table text-color text-center">
              <thead className="table-bordered border-color bg-color custom-bg-text">
                <tr>
                  <th scope="col">Policy Name</th>
                  <th scope="col">Policy Id</th>
                  <th scope="col">Application Date</th>
                  <th scope="col">Start Date</th>
                  <th scope="col">End Date</th>
                  <th scope="col">Status</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {applications.map((application) => {
                  return (
                    <tr>
                      <td>
                        <b>{application.policy.name}</b>
                      </td>
                      <td>
                        <b>{application.policy.policyId}</b>
                      </td>
                      <td>
                        <b>
                          {formatDateFromEpoch(application.applicationDate)}
                        </b>
                      </td>
                      <td>
                        <b>
                          {!application.startDate
                            ? "Approval Pending"
                            : application.startDate}
                        </b>
                      </td>
                      <td>
                        <b>
                          {!application.endDate
                            ? "Approval Pending"
                            : application.startDate}
                        </b>
                      </td>
                      <td>
                        <b>{application.status}</b>
                      </td>
                      <td>
                        <b>
                          {(() => {
                            if (application.status === "Approved") {
                              return (
                                <button
                                  onClick={() => claimPolicy(application)}
                                  className="btn btn-sm bg-color custom-bg-text"
                                >
                                  <b> Claim</b>
                                </button>
                              );
                            }
                          })()}
                        </b>
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
  );
};

export default ViewPolicyApplication;
