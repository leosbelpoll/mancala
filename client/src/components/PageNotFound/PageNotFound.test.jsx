import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { BrowserRouter as Router } from "react-router-dom";
import { act } from "react-dom/test-utils";

import PageNotFound from "./PageNotFound";

let container = null;
beforeEach(() => {
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("render simple page not found", () => {
  act(() => {
    render(
      <Router>
        <PageNotFound />
      </Router>,
      container
    );
  });
  const errorTitle = container.querySelector(".big-title-error");
  expect(errorTitle.textContent).toBe("Sorry");

  const errorDesciption = container.querySelector("h3");
  expect(errorDesciption.textContent).toBe(
    " The page you are looking for doesn't exist"
  );

  const starterText = container.querySelector("h6");
  expect(starterText.textContent).toBe("Click the button to start a new game");

  const starterButton = container.querySelector(".btn-primary");
  expect(starterButton.textContent).toBe(" New game");
});
