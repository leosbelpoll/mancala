import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";

import Pit from "./Pit";

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

it("render simple pit", () => {
  act(() => {
    render(<Pit ballsInPit={3} />, container);
  });
  const wrapper = container.querySelector(".pit-wrapper");
  expect(wrapper.textContent).toBe("3");
  expect(wrapper.getAttribute("class")).toBe("pit-wrapper");

  const pit = wrapper.querySelector(".pit");
  expect(pit.getAttribute("class")).toBe("pit hole");
});

it("render disabled pit", () => {
  act(() => {
    render(<Pit ballsInPit={6} disabled />, container);
  });
  const wrapper = container.querySelector(".pit-wrapper");
  expect(wrapper.textContent).toBe("6");
  expect(wrapper.getAttribute("class")).toBe("pit-wrapper disabled");
});
