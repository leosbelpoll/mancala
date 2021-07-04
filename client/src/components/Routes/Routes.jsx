import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Header from "components/Header";
import PageNotFound from "components/PageNotFound";
import Configuration from "components/Configuration";

const Routes = () => {
  return (
    <div className="app-component">
      <Router>
        <Header />
        <Switch>
          <Route path="/configuration" component={Configuration} exact />
          <Route component={PageNotFound} />
        </Switch>
      </Router>
    </div>
  );
};

export default Routes;
