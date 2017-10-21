defmodule Fortunebot.Bot do

  def auth(code) do
    "https://slack.com/api/oauth.access" <> 
    "?code=#{code}&client_id=#{System.get_env("CLIENT_ID")}&client_secret=#{System.get_env("CLIENT_SECRET")}"
    |> HTTPoison.get
    |> handle_oauth_access_response
  end

  defp handle_oauth_access_response({:ok, %HTTPoison.Response{body: body}}) do
    case Poison.Parser.parse(body, keys: :atoms) do
      {:ok, %{ok: true} = json} -> {:ok, json}
      {:ok, %{ok: false, error: reason}} -> {:error, reason}
      {:error, _} -> {:error, "Error parsing body"}
    end
  end

  defp handle_oauth_access_response(error), do: error

  def process_event(%{"type" => "message", "text" => text, "channel" => channel, "user" => user}) do
    bot_auth_info = Fortunebot.LocalDb.get_bot_auth_info
    if bot_auth_info != nil && !bot_user?(bot_auth_info, user) do
      post_message(bot_auth_info, channel, text)
    end
    :ok
  end

  def process_event(_event), do: :ok

  defp bot_user?(bot_auth_info, user) do
    #  TODO: Validate if the Slack event comes from the Bot User
    false
  end

  defp empty?(string) do
    string == nil or String.length(string) == 0
  end

  defp post_message(bot_auth_info, channel, text) do
    # TODO: POST https://slack.com/api/chat.postMessage to echo the Event message in text
    # TIP: Use HTTPoison.post
    # TIP: To send form params in HTTPoison.post you have specify a tuple as the second argument like: {:form, <key-value list>}
    # TIP: Don't forget to specify the Content-Type as application/x-www-form-urlencoded
  end
end