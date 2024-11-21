import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";

const AddClaim = () => {
  let navigate = useNavigate();
  const location = useLocation();
  var application = location.state;

  const [claimRequest, setClaimRequest] = useState({
    customerId: application.user.id,
    policyApplicationId: application.id,
    policyId: application.policy.id,
  });

  const handleUserInput = (e) => {
    setClaimRequest({ ...claimRequest, [e.target.name]: e.target.value });
  };

  const addClaim = (e) => {
    fetch("http://localhost:9000/api/claim/add", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(claimRequest),
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
    e.preventDefault();
  };

  return (
    <div>
      <div className="mt-2 d-flex aligns-items-center justify-content-center">
        <div className="form-card" style={{ width: "25rem" }}>
          <div className="container-fluid">
            <div
              className="card-header bg-color custom-bg-text mt-2 d-flex justify-content-center align-items-center"
              style={{
                borderRadius: "1em",
                height: "38px",
              }}
            >
              <h4 className="card-title">Add Claim</h4>
            </div>
            <div className="card-body mt-3">
              <form className="text-color">
                <div className="mb-3">
                  <label for="emailId" class="form-label">
                    <b>Accident Date</b>
                  </label>
                  <input
                    type="date"
                    className="form-control"
                    id="accidentDate"
                    name="accidentDate"
                    onChange={handleUserInput}
                    value={claimRequest.accidentDate}
                  />
                </div>

                <div className="mb-3">
                  <label for="emailId" class="form-label">
                    <b>Claim Amount</b>
                  </label>
                  <input
                    type="number"
                    className="form-control"
                    id="emailId"
                    name="claimAmount"
                    onChange={handleUserInput}
                    value={claimRequest.claimAmount}
                  />
                </div>

                <div className="d-flex aligns-items-center justify-content-center mb-2">
                  <button
                    type="submit"
                    className="btn bg-color custom-bg-text"
                    onClick={addClaim}
                  >
                    <b> Add Claim</b>
                  </button>
                  <ToastContainer />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddClaim;
