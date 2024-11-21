import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { Button, Modal } from "react-bootstrap";

const ViewAllPolicyApplication = () => {
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

  const [application, setApplication] = useState({});
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [showModal, setShowModal] = useState(false);

  const handleClose = () => setShowModal(false);
  const handleShow = () => setShowModal(true);

  const showApproveModal = (application) => {
    setApplication(application);
    handleShow();
  };

  const retrieveApplication = async () => {
    const response = await axios.get(
      "http://localhost:9000/api/policy/application/fetch/all",
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

  const updateApplicationStatus = (
    applicationId,
    status,
    startDate,
    endDate
  ) => {
    fetch("http://localhost:9000/api/policy/application/status/update", {
      method: "PUT",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        policyApplicationId: applicationId,
        status: status,
        startDate: startDate,
        endDate: endDate,
      }),
    })
      .then((result) => {
        console.log("result", result);
        result.json().then((res) => {
          if (res.success) {
            toast.success(res.responseMessage, {
              position: "top-center",
              autoClose: 3000,
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
              autoClose: 2000,
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
                            if (application.status === "Pending") {
                              return (
                                <div>
                                  <button
                                    onClick={() =>
                                      showApproveModal(application)
                                    }
                                    className="btn btn-sm bg-success text-dark"
                                  >
                                    <b> Approve</b>
                                  </button>

                                  <button
                                    onClick={() =>
                                      updateApplicationStatus(
                                        application.id,
                                        "Rejected",
                                        null,
                                        null
                                      )
                                    }
                                    className="btn btn-sm bg-danger text-white"
                                  >
                                    <b> Reject</b>
                                  </button>
                                </div>
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

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton className="bg-color custom-bg-text">
          <Modal.Title
            style={{
              borderRadius: "1em",
            }}
          >
            Approve Customer Policy Application
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className="ms-3 mt-3 mb-3 me-3">
            <form>
              <div class="mb-3">
                <label for="title" class="form-label">
                  <b>Policy Name</b>
                </label>
                <input
                  type="text"
                  class="form-control"
                  value={
                    application && application.policy && application.policy.name
                  }
                  readOnly
                />
              </div>
              <div class="mb-3">
                <label for="title" class="form-label">
                  <b>Policy Id</b>
                </label>
                <input
                  type="text"
                  class="form-control"
                  value={
                    application &&
                    application.policy &&
                    application.policy.policyId
                  }
                  readOnly
                />
              </div>

              <div class="mb-3">
                <label for="title" class="form-label">
                  <b>Policy Start Date</b>
                </label>
                <input
                  type="date"
                  class="form-control"
                  onChange={(e) => setStartDate(e.target.value)}
                  value={startDate}
                  required
                />
              </div>

              <div class="mb-3">
                <label for="title" class="form-label">
                  <b>Policy End Date</b>
                </label>
                <input
                  type="date"
                  class="form-control"
                  onChange={(e) => setEndDate(e.target.value)}
                  value={endDate}
                  required
                />
              </div>

              <div className="d-flex aligns-items-center justify-content-center mb-2">
                <button
                  type="submit"
                  onClick={() =>
                    updateApplicationStatus(
                      application.id,
                      "Approved",
                      startDate,
                      endDate
                    )
                  }
                  class="btn text-dark bg-success"
                >
                  Approve
                </button>
                <ToastContainer />
              </div>

              <ToastContainer />
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

export default ViewAllPolicyApplication;
