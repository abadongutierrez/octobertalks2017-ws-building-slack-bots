defmodule FortunebotWeb.PageController do
  use FortunebotWeb, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end

  def install(conn, _params) do
  	render conn, "install.html", client_id: System.get_env("CLIENT_ID")
  end

  def thanks(conn, params) do
    if params["code"] do
      {:ok, response} = Fortunebot.Bot.auth(params["code"])
      Fortunebot.LocalDb.set_bot_auth_info(response.bot)
    end
  	render conn, "thanks.html"
  end

  def slack(conn, params) do
    return_text = cond do
      params["token"] != System.get_env("VERIFICATION_TOKEN") ->
        "Ignoring request, I don't recognize you"
      params["challenge"] != nil and params["type"] == "url_verification" ->
        params["challenge"]
      params["event"] != nil ->
        spawn(fn ->
          Fortunebot.Bot.process_event(params["event"])
        end)
        "Ok"
      true ->
        "Ok"
    end
    text conn, return_text
  end
end
