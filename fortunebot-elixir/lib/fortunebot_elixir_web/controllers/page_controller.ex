defmodule FortunebotWeb.PageController do
  use FortunebotWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end

  def install(conn, _params) do
    render conn, "install.html"
  end

  def thanks(conn, _params) do
    render conn, "thanks.html"
  end

  def slack(conn, _params) do
    text conn, "Ok"
  end

end
