defmodule FortunebotWeb.Router do
  use FortunebotWeb, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    #plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", FortunebotWeb do
    pipe_through :browser # Use the default browser stack

    get "/", PageController, :index

    # TODO: add mapping GET /install for PageController.install
    # TODO: add mapping GET /thanks for PageController.thanks
    # TODO: add mapping POST /slack for PageController.slack
  end

  # Other scopes may use custom stacks.
  # scope "/api", FortunebotWeb do
  #   pipe_through :api
  # end
end
