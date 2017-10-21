defmodule FortunebotWeb.PageController do
  use FortunebotWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end

  # TODO: add install function to render "install.html"

  # TODO: add thanks function to render "thanks.html"

  # TODO: add slack function to render text "Ok"

end
