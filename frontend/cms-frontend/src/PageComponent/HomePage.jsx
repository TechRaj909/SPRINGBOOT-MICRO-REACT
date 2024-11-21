import React, { useState, useEffect } from "react";
import axios from "axios";
import Carousel from "./Carousel";
import Footer from "../NavbarComponent/Footer";
import { useNavigate } from "react-router-dom";
import PolicyCard from "../PolicyComponent/PolicyCard";

const HomePage = () => {
  const navigate = useNavigate();

  const [policies, setPolicies] = useState([]);

  const retrieveAllPolicy = async () => {
    const response = await axios.get(
      "http://localhost:9000/api/policy/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllPolicy = async () => {
      const res = await retrieveAllPolicy();
      if (res) {
        setPolicies(res.policies);
      }
    };
    getAllPolicy();
  }, []);

  return (
    <div
      className="container-fluid mb-2"
      style={{
        backgroundColor: "#1f1f1f",
      }}
    >
      <Carousel />
      <div className="col-md-12 mt-3 mb-5">
        <div className="row row-cols-1 row-cols-md-2 g-4">
          {policies.map((policy) => {
            return <PolicyCard item={policy} key={policy.id} />;
          })}
        </div>
      </div>
    </div>
  );
};

export default HomePage;
