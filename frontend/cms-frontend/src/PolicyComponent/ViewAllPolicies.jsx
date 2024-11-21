import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const ViewAllPolicies = () => {
  let navigate = useNavigate();

  const [applications, setApplications] = useState([]);
  //const admin_jwtToken = sessionStorage.getItem("admin-jwtToken");

  useEffect(() => {
    const getApplication = async () => {
      const application = await retrieveApplication();
      if (application) {
        setApplications(application.policies);
      }
    };

    getApplication();
  }, []);

  const retrieveApplication = async () => {
    const response = await axios.get(
      "http://localhost:9000/api/policy/fetch/all"
    );
    return response.data;
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const viewPolicy = (policy) => {
    navigate(`/policy/${policy.id}/detail`);
  };

  const deletePolicy = (policyId) => {
    fetch("http://localhost:9000/api/policy/delete?policyId=" + policyId, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
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
              window.location.reload(true);
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
          <h2>All Policies</h2>
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
                  <th scope="col">Description</th>
                  <th scope="col">Premium Amount</th>
                  <th scope="col">Plan</th>
                  <th scope="col">Status</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {applications.map((policy) => {
                  return (
                    <tr>
                      <td>
                        <b>{policy.name}</b>
                      </td>
                      <td>
                        <b>{policy.policyId}</b>
                      </td>
                      <td>
                        <b>{policy.description}</b>
                      </td>
                      <td>
                        <b>{policy.premiumAmount}</b>
                      </td>
                      <td>
                        <b>{policy.plan}</b>
                      </td>
                      <td>
                        <b>{policy.status}</b>
                      </td>
                      <td>
                        <b>
                          <button
                            onClick={() => viewPolicy(policy)}
                            className="btn btn-sm bg-color custom-bg-text"
                          >
                            <b> View Detail</b>
                          </button>
                        </b>
                        <b>
                          <button
                            onClick={() => deletePolicy(policy.id)}
                            className="btn btn-sm bg-danger text-dark"
                          >
                            <b>Delete</b>
                          </button>
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

export default ViewAllPolicies;
