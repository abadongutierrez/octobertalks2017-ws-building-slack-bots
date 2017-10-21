defmodule FortunebotWeb.PageController do
  use FortunebotWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end

  def install(conn, _params) do
    # TODO: send 'client_id' in the assigns to be used by the view with the value of the CLIENT_ID environment variable
    render conn, "install.html"
  end

  def thanks(conn, params) do
    if params["code"] do
      {:ok, response} = Fortunebot.Bot.auth(params["code"])
      Fortunebot.LocalDb.set_bot_auth_info(response.bot)
    end
    render conn, "thanks.html"
  end

  def slack(conn, _params) do
    text conn, "Ok"
  end

end
