import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const ViewMyClaims = () => {
  let navigate = useNavigate();
  const customer = JSON.parse(sessionStorage.getItem("active-customer"));

  const [applications, setApplications] = useState([]);
  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  useEffect(() => {
    const getApplication = async () => {
      const application = await retrieveApplication();
      if (application) {
        setApplications(application.claims);
      }
    };

    getApplication();
  }, []);

  const retrieveApplication = async () => {
    const response = await axios.get(
      "http://localhost:9000/api/claim/fetch/customer-wise?customerId=" +
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

  const updateMyClaim = (claimId, status) => {
    fetch("http://localhost:9000/api/claim/customer/response", {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        claimId: claimId,
        customerClaimResponse: status,
      }),
    })
      .then((result) => {
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
              window.location.href = "/home";
            }, 1000); // Redirect after 3 seconds
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
          <h2>My Claims</h2>
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
                  <th scope="col">Claim Date</th>
                  <th scope="col">Claim Amount</th>
                  <th scope="col">Accident Date</th>
                  <th scope="col">Amount Approved</th>
                  <th scope="col">Claim Status</th>
                  <th scope="col">Action Status</th>
                  <th scope="col">Customer Response</th>
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
                        <b>
                          {formatDateFromEpoch(
                            application.claim.claimApplicationDate
                          )}
                        </b>
                      </td>
                      <td>
                        <b>{application.claim.claimAmount}</b>
                      </td>
                      <td>
                        <b>
                          <b>{application.claim.dateOfAccident}</b>
                        </b>
                      </td>

                      <td>
                        <b>
                          {!application.claim.amtApprovedBySurveyor
                            ? "NA"
                            : application.claim.amtApprovedBySurveyor}
                        </b>
                      </td>
                      <td>
                        <b>
                          <b>{application.claim.claimStatus}</b>
                        </b>
                      </td>
                      <td>
                        <b>
                          <b>{application.claim.actionStatus}</b>
                        </b>
                      </td>
                      <td>
                        <b>
                          <b>{application.claim.customerClaimResponse}</b>
                        </b>
                      </td>
                      <td>
                        {(() => {
                          if (application.claim.claimStatus === "Open") {
                            return (
                              <div>
                                <button
                                  onClick={() =>
                                    updateMyClaim(
                                      application.claim.id,
                                      "Accept"
                                    )
                                  }
                                  className="btn btn-sm bg-success text-dark"
                                >
                                  <b> Accept</b>
                                </button>

                                <button
                                  onClick={() =>
                                    updateMyClaim(
                                      application.claim.id,
                                      "Withdraw"
                                    )
                                  }
                                  className="btn btn-sm bg-danger text-white"
                                >
                                  <b> Withdraw</b>
                                </button>
                              </div>
                            );
                          }
                        })()}
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

export default ViewMyClaims;
