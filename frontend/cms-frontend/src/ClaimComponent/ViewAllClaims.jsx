import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";
import { ToastContainer, toast } from "react-toastify";

const ViewAllClaims = () => {
  let navigate = useNavigate();
  const customer = JSON.parse(sessionStorage.getItem("active-customer"));

  const [applications, setApplications] = useState([]);
  const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  const [surveyorId, setSurveyorId] = useState("");

  const [surveyors, setSurveyors] = useState([]);

  const [claimId, setClaimId] = useState("");

  useEffect(() => {
    const getAllUsers = async () => {
      const allUsers = await retrieveAllUser();
      if (allUsers) {
        setSurveyors(allUsers.users);
      }
    };

    getAllUsers();
  }, []);

  const retrieveAllUser = async () => {
    const response = await axios.get(
      "http://localhost:9000/api/user/fetch/all?role=surveyor",
      {
        headers: {
          //   Authorization: "Bearer " + admin_jwtToken, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
  };

  const [showModal, setShowModal] = useState(false);

  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);

  const assignClaimId = (claimId, e) => {
    setClaimId(claimId);
    handleShow();
  };

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
      "http://localhost:9000/api/claim/fetch/all",
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

  const updateClaim = (e) => {
    e.preventDefault();

    fetch("http://localhost:9000/api/claim/assign/surveyor", {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        claimId: claimId,
        surveyorId: surveyorId,
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
          <h2>All Claims</h2>
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
                  <th scope="col">Customer Name</th>
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
                          {application.customer.firstName +
                            " " +
                            application.customer.lastName}
                        </b>
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
                          if (application.claim.actionStatus === "Pending") {
                            return (
                              <button
                                onClick={() =>
                                  assignClaimId(application.claim.id)
                                }
                                className="btn btn-sm bg-color custom-bg-text"
                              >
                                <b>Assign Surveyor</b>
                              </button>
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

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton className="bg-color custom-bg-text">
          <Modal.Title
            style={{
              borderRadius: "1em",
            }}
          >
            Assign Surveyor
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="ms-3 mt-3 mb-3 me-3">
            <form>
              <div className=" mb-3">
                <label className="form-label">
                  <b>Surveyor</b>
                </label>

                <select
                  name="vehicleId"
                  onChange={(e) => setSurveyorId(e.target.value)}
                  className="form-control"
                >
                  <option value="">Select Surveyor</option>

                  {surveyors.map((surveyor) => {
                    return (
                      <option value={surveyor.user.id}>
                        {surveyor.user.firstName}
                      </option>
                    );
                  })}
                </select>
              </div>

              <div className="d-flex aligns-items-center justify-content-center mb-2">
                <button
                  type="submit"
                  onClick={updateClaim}
                  class="btn bg-color custom-bg-text"
                >
                  Assign Surveyor
                </button>
                <ToastContainer />
              </div>
            </form>
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default ViewAllClaims;
