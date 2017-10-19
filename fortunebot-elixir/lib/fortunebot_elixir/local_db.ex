defmodule Fortunebot.LocalDb do
    
  def set_bot_auth_info(info) do
    {:ok, agent_pid} = if Process.whereis(__MODULE__) == nil do
       Agent.start_link fn -> info end
    else
      Process.unregister(__MODULE__)
      Agent.start_link fn -> info end
    end
    Process.register agent_pid, __MODULE__
    :ok
  end

  def get_bot_auth_info() do
    agent_pid = Process.whereis(__MODULE__)
    Agent.get(agent_pid, fn info -> info end)
  end
end