import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";

const AddPolicyForm = () => {
  let navigate = useNavigate();

  const [addRequest, setAddRequest] = useState({
    coverageDetails: [],
  });

  const [coverageDetails, setCoverageDetails] = useState([]);

  const [coverageDetail, setCoverageDetail] = useState({
    type: "",
    description: "",
    amount: "",
  });

  const handleUserInput = (e) => {
    setAddRequest({
      ...addRequest,
      [e.target.name]: e.target.value,
    });
  };

  const handleUserCoverageInput = (e) => {
    setCoverageDetail({
      ...coverageDetail,
      [e.target.name]: e.target.value,
    });
  };

  const addCoverage = (e) => {
    e.preventDefault();
    // Validate if the coverageDetail object has all required fields
    if (
      !coverageDetail.type ||
      !coverageDetail.description ||
      !coverageDetail.amount
    ) {
      // If any field is missing, you can show an error message or handle it as per your requirement
      alert("Please fill in all fields");
      return;
    }

    // Create a copy of the coverageDetails array
    const newCoverageDetails = [...coverageDetails];
    // Push the new coverageDetail object into the copy
    newCoverageDetails.push(coverageDetail);

    // Update the state with the new array including the added coverageDetail
    setCoverageDetails(newCoverageDetails);

    // Clear the input fields for the next entry
    setCoverageDetail({
      type: "",
      description: "",
      amount: "",
    });
  };

  const addPolicy = (e) => {
    e.preventDefault();
    if (!addRequest.plan) {
      alert("Please select policy plan!!!");
    } else if (!coverageDetails) {
      alert("Please add the Coverage Details!!!");
    } else {
      addRequest.coverageDetails = coverageDetails;
      fetch("http://localhost:9000/api/policy/add", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(addRequest),
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
                  <h4 className="card-title">Add Policy</h4>
                </div>
                <div className="card-body mt-3">
                  <form className="text-color">
                    <div className="mb-3">
                      <label for="name" class="form-label">
                        <b>Name</b>
                      </label>
                      <input
                        type="text"
                        className="form-control"
                        id="name"
                        name="name"
                        onChange={handleUserInput}
                        value={addRequest.name}
                      />
                    </div>
                    <div className="mb-3">
                      <label for="description" class="form-label">
                        <b>Description</b>
                      </label>
                      <textarea
                        type="text"
                        className="form-control"
                        id="description"
                        name="description"
                        onChange={handleUserInput}
                        value={addRequest.description}
                      />
                    </div>

                    <div className="mb-3">
                      <label className="form-label">
                        <b>Policy Plan</b>
                      </label>
                      <select
                        name="plan"
                        onChange={handleUserInput}
                        className="form-control"
                      >
                        <option value="">Select Plan</option>
                        <option value="Monthly">Monthly</option>
                        <option value="Quaterly">Quaterly</option>
                        <option value="Yearly">Yearly</option>
                      </select>
                    </div>

                    <div className="mb-3">
                      <label for="name" class="form-label">
                        <b>Premium Amount</b>
                      </label>
                      <input
                        type="number"
                        className="form-control"
                        id="name"
                        name="premiumAmount"
                        onChange={handleUserInput}
                        value={addRequest.premiumAmount}
                      />
                    </div>
                    <div className="d-flex aligns-items-center justify-content-center mb-2">
                      <button
                        type="submit"
                        className="btn bg-color custom-bg-text"
                        onClick={addPolicy}
                      >
                        <b> Add Policy</b>
                      </button>
                      <ToastContainer />
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
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
                  <h4 className="card-title">Add Coverage</h4>
                </div>
                <div className="card-body mt-3">
                  <form className="text-color">
                    <div className="mb-3">
                      <label className="form-label">
                        <b>Coverage Type</b>
                      </label>
                      <select
                        name="type"
                        onChange={handleUserCoverageInput}
                        className="form-control"
                      >
                        <option value="">Select Type</option>
                        <option value="Collision">Collision</option>
                        <option value="Comprehensive">Comprehensive</option>
                        <option value="Liability">Liability</option>
                        <option value="Uninsured Motorist">
                          Uninsured Motorist
                        </option>
                        <option value="Medical Payment">Medical Payment</option>
                        <option value="Roadside Assistance">
                          Roadside Assistance
                        </option>
                        <option value="Rental Reimbursement">
                          Rental Reimbursement
                        </option>
                        <option value="Gap Insurance">Gap Insurance</option>
                      </select>
                    </div>
                    <div className="mb-3">
                      <label for="description" class="form-label">
                        <b>Description</b>
                      </label>
                      <textarea
                        type="text"
                        className="form-control"
                        id="description"
                        name="description"
                        onChange={handleUserCoverageInput}
                        value={coverageDetail.description}
                      />
                    </div>
                    <div className="mb-3">
                      <label for="name" class="form-label">
                        <b>Coverage Amount</b>
                      </label>
                      <input
                        type="number"
                        className="form-control"
                        id="name"
                        name="amount"
                        onChange={handleUserCoverageInput}
                        value={coverageDetail.amount}
                      />
                    </div>
                    <div className="d-flex aligns-items-center justify-content-center mb-2">
                      <button
                        type="submit"
                        className="btn bg-color custom-bg-text"
                        onClick={addCoverage}
                      >
                        <b> Add Coverage</b>
                      </button>
                      <ToastContainer />
                    </div>
                  </form>
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
                        {coverageDetails.map((coverage) => {
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

export default AddPolicyForm;
