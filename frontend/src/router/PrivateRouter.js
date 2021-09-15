import React from "react";
import { Route, Redirect } from "react-router-dom";
// import { useSelector } from "react-redux";

const PrivateRouter = ({ component: Component, ...rest }) => {
  //   const currentUser = useSelector((state) => state.currentUser);
  const currentUser = { login: true };
  return (
    <Route
      {...rest}
      render={(props) => {
        !currentUser.login && alert("로그인이 필요한 페이지입니다.");
        return currentUser.login ? (
          <Component {...props} />
        ) : (
          <Redirect to="/" />
        );
      }}
    />
  );
};

export default PrivateRouter;
