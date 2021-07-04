import { getEnv } from "./env";

const apiUrl = getEnv("API_URL");

const getEndpoint = (resource) => `${apiUrl}/${resource}`;

export const postData = async (resource, data) => {
  return sendData("POST", resource, data);
};

export const putData = async (resource, data) => {
  return sendData("PUT", resource, data);
};

export const getData = async (resource) => {
  const response = await fetch(getEndpoint(resource));
  const json = await response.json();

  return json;
};

const sendData = async (method, resource, data) => {
  const response = await fetch(getEndpoint(resource), {
    method,
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const message = await response.text();
    throw Error(message);
  }

  const json = await response.json();

  return json;
};
